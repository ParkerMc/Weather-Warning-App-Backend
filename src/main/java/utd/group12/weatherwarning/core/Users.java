package utd.group12.weatherwarning.core;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import utd.group12.weatherwarning.Utils;
import utd.group12.weatherwarning.data.DataToken;
import utd.group12.weatherwarning.data.DataUser;
import utd.group12.weatherwarning.data.IDataUsers;
import utd.group12.weatherwarning.errors.BadRequestError;
import utd.group12.weatherwarning.errors.ConflictError;
import utd.group12.weatherwarning.errors.NotFoundError;
import utd.group12.weatherwarning.errors.UnathorizedError;

/**
 * Handles everything related to users
 */
public class Users {
	private final static int PASSWORD_ITERATIONS = 10000;	// iterations to hash the password with
    private final static int PASSWORD_KEY_LENGTH = 512;		// the key length for the password
    private final static int PASSWORD_SALT_LENGTH = 32;		// the salt length for the password
    
    // Regexs to use
    private final static String VALID_EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final static String VALID_USERNAME_REGEX = "^[a-zA-Z0-9]+$";
	
	private final Core core;
	private IDataUsers data;
	
	public Users(Core core) {
		this.core = core;
	}
	
	/**
	 * When everything is starting up
	 */
	void start() {
		this.data = core.data.getUsers();
	}
	
	/**
	 * Generates the password hash
	 * 
	 * @param password	the password to hash
	 * @param salt		the salt to hash with
	 * @return			the hashed password
	 */
    private byte[] hashPassword(String password, String salt) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), PASSWORD_ITERATIONS, PASSWORD_KEY_LENGTH);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded( );
            return res;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
	 * Creates a new user
	 * 
	 * @param username			the new user's username
	 * @param email				the new user's email
	 * @param password			the new user's password
	 * @param name				the user's name
	 * @param phoneNumber		the new user's phone number
	 * @return					the username, token, and token experation
	 * @throws ConflictError	if the user or email is already used
	 * @throws BadRequestError 	if the username or email is invalid
	 */
	public UsernameTokenPair create(String username, String email, String password, String name, String phoneNumber) throws ConflictError, BadRequestError {		
		if(!Utils.matchRegex(username, VALID_USERNAME_REGEX)) {	// Make sure username is valid
			throw new BadRequestError("username invalid");
		}
		if(!Utils.matchRegex(email, VALID_EMAIL_REGEX)) {		// Make sure email is valid
			throw new BadRequestError("email invalid");
		}
		if(password.length() < 5) {								// Make sure the password is long enough
			throw new BadRequestError("password too short");
		}
		if(this.data.exists(username.toLowerCase())) {	// if the user is already used throw error 
			throw new ConflictError("That username has already been used");	
		}
		if(this.data.isEmailUsed(email)) {	// if the email is already used throw error 
			throw new ConflictError("That email has already been used");	
		}
		
		String salt = Utils.generateRndString(PASSWORD_SALT_LENGTH);
		String hashedPassword = Base64.getEncoder().encodeToString(hashPassword(password, salt));
		
		// Create the user
		DataUser user = this.data.create(username.toLowerCase(), email, null, hashedPassword, salt, name, phoneNumber);
		
		try {
			return new UsernameTokenPair(user.getUsername(), this.core.tokens.create(user.getUsername()));
		} catch (NotFoundError e) {
			throw new RuntimeException(); // Should not ever happen as we know the user exists
		}
	}
	
	/**
	 * Check if the user exits
	 * 
	 * @param username	the username to check for
	 * @return			if the user exits
	 */
	public boolean exists(String username) {
		return this.data.exists(username);
	}
	
	/**
	 * Gets the requested user
	 * 
	 * @param username			The username for the user to get 
	 * @return					The user
	 * @throws NotFoundError	If the user was not found
	 */
	public DataUser get(String username) throws NotFoundError {
		return this.data.get(username.toLowerCase());
	}
	
	/**
	 * Processes login from google
	 * 
	 * @param ID		The user's google ID 
	 * @param email		The user's email
	 * @return			{@code UsernameTokenPair} that contains the username, token, and token expiration
	 */
	public UsernameTokenPair googleLogin(String ID, String email) {		
		// Get the user
		DataUser user;
		try {
			user = this.data.getFromGoogleID(ID);
		} catch (NotFoundError e) {	// If the user does not exist create it
			String username;
			do {
				username = Utils.generateRndString(15).toLowerCase();						// Generate a username
			} while(this.data.exists(username));				// and make sure it is unique
			try {
				user = this.data.create(username, email, ID, null, null, "", "");
			} catch (ConflictError e1) {
				throw new RuntimeException(); // Because we check if username is use there will be no error
			}	
		}
		
		try {
			return new UsernameTokenPair(user.getUsername(), this.core.tokens.create(user.getUsername()));
		} catch (NotFoundError e) {
			throw new RuntimeException(); // Should not ever happen as we know the user exists
		}
	}
	
	/**
	 * Throws an error if the user is not properly authenticated
	 * 
	 * @param username			the username to authenticate with
	 * @param token				the token to authenticate with
	 * @throws UnathorizedError	if the user is not authenticated
	 */
	public void requireLogin(String username, String token) throws UnathorizedError {
		if(!isLoggedIn(username.toLowerCase(), token)) {	// If the user is not logged throw Unauthorized
			throw new UnathorizedError("You are not authenticated.");
		}
	}

	/**
	 * Checks if the user is properly authenticated
	 * 
	 * @param username	username to check for
	 * @param token		token to check for
	 * @return			if the user is authenticated
	 */
	public boolean isLoggedIn(String username, String token) {
		return this.core.tokens.exists(username, token);
	}
	
	/**
	 * Logs a user in
	 * 
	 * @param identifier		the user's username or email
	 * @param password			the user's password
	 * @return					{@code UsernameTokenPair} that contains the username, token, and token expiration
	 * @throws UnathorizedError When the identifier can't be used or the password is wrong
	 */
	public UsernameTokenPair login(String identifier, String password) throws UnathorizedError {
		DataUser user;
		try {
			user = this.data.get(identifier.toLowerCase());	// First try getting the user by username
		} catch (NotFoundError e) {								// If that doesn't work try by email
			try {
				user = this.data.getFromEmail(identifier);
			} catch (NotFoundError e1) {
				throw new UnathorizedError("Username or password is wrong.");
			}
		}
		
		String hashedPassword = Base64.getEncoder().encodeToString(hashPassword(password, user.getSalt()));
		if(!user.getPassword().equals(hashedPassword)) {
			throw new UnathorizedError("Username or password is wrong.");
		}
		try {
			return new UsernameTokenPair(user.getUsername(), this.core.tokens.create(user.getUsername()));
		} catch (NotFoundError e) {
			throw new RuntimeException(); // Should not ever happen as we know the user exists
		}
	}
	
	/**
	 * Logs out the user
	 * 
	 * @param username			the username to logout from
	 * @param token				the token being used
	 * @throws NotFoundError 	if the user does not exist
	 */
	public void logout(String username, String token) throws NotFoundError {
		this.core.tokens.remove(username, token);
	}
	
	/**
	 * Used to return the username, token, and token expiration when someone logs in
	 * Only has the constructor and getters
	 */
	public static class UsernameTokenPair{
		private final String username;
		private final DataToken token;
		
		public UsernameTokenPair(String username, DataToken token) {
			this.username = username;
			this.token = token;
		}

		public String getUsername() {
			return username;
		}

		public DataToken getToken() {
			return token;
		}			
	}
}
