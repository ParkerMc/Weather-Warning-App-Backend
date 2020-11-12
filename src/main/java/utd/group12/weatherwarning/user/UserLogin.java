package utd.group12.weatherwarning.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import utd.group12.weatherwarning.Utils;
import utd.group12.weatherwarning.WeatherWarningApplication;
import utd.group12.weatherwarning.data.DataUser;
import utd.group12.weatherwarning.data.IDataUsers;
import utd.group12.weatherwarning.errors.BadRequestError;
import utd.group12.weatherwarning.errors.ConflictionError;

/**
 * Handles processing user login and account creation
 */
public class UserLogin {
	private final static int EXPIRATION_DAYS = 30;			// The number of days each token should stay valid for
	private final static int PASSWORD_ITERATIONS = 10000;	// iterations to hash the password with
    private final static int PASSWORD_KEY_LENGTH = 512;		// the key length for the password
    private final static int PASSWORD_SALT_LENGTH = 32;		// the salt length for the password
    
    // Regexs to use
    private final static String VALID_EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final static String VALID_USERNAME_REGEX = "^[a-zA-Z0-9]+$";
	
	/**
	 * Generates a random string with 0-9,a-z,A-Z of requested length
	 * 
	 * @param len	The length of the random string
	 * @return		The random string
	 */
	private static String generateRndString(int len) {
        String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";	// Chars allowed
        StringBuilder str = new StringBuilder();
        Random rnd = new Random();
        while (str.length() < len) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            str.append(CHARS.charAt(index));
        }
        return str.toString();
    }
	
    private static byte[] hashPassword(String password, String salt) {
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
	 * @param phoneNumber		the new user's phone number
	 * @return					the username, token, and token experation
	 * @throws ConflictionError	if the user or email is already used
	 * @throws BadRequestError 
	 */
	public static UsernameTokenPair createUser(String username, String email, String password, String phoneNumber) throws ConflictionError, BadRequestError {
		IDataUsers dataUsers = WeatherWarningApplication.data.getUsers();	// Make data easier to access
		
		if(!Utils.matchRegex(username, VALID_USERNAME_REGEX)) {	// Make sure username is valid
			throw new BadRequestError("Invalid Username.");
		}
		if(!Utils.matchRegex(email, VALID_EMAIL_REGEX)) {		// Make sure email is valid
			throw new BadRequestError("Invalid Email.");
		}
		if(dataUsers.isUsernameUsed(username)) {	// if the user is already used throw error 
			throw new ConflictionError("username used");	
		}
		if(dataUsers.isEmailUsed(email)) {	// if the email is already used throw error 
			throw new ConflictionError("email used");	
		}
		
		String salt = generateRndString(PASSWORD_SALT_LENGTH);
		String hashedPassword = Base64.getEncoder().encodeToString(hashPassword(password, salt));
		
		// Create the user
		DataUser user = dataUsers.createUser(username, email, null, hashedPassword, salt, phoneNumber);
		if(user == null) {	// if the user is null an error occurred and return null
			return null;
		}
				
		// Generate token and expiration date/time
		Date tokenExp = Date.from(Instant.now().plus(Duration.ofDays(EXPIRATION_DAYS)));
		String token;
		do {
			token = generateRndString(50);
		}while(dataUsers.isTokenUsed(token));	// Make sure this is a unique token
		
		// Add token and return
		dataUsers.addToken(user.getUsername(), token, tokenExp);
		return new UsernameTokenPair(user.getUsername(), token, tokenExp);
	}

	/**
	 * Processes login from google
	 * 
	 * @param ID		The user's google ID 
	 * @param email		The user's email
	 * @return			{@code UsernameTokenPair} that contains the username, token, and token expiration
	 */
	@Nullable
	public static UsernameTokenPair googleLogin(String ID, String email) {
		IDataUsers dataUsers = WeatherWarningApplication.data.getUsers();	// Make data easier to access
		
		// Generate token and expiration date/time
		Date tokenExp = Date.from(Instant.now().plus(Duration.ofDays(EXPIRATION_DAYS)));
		String token;
		do {
			token = generateRndString(50);
		}while(dataUsers.isTokenUsed(token));	// Make sure this is a unique token
		
		// Get the user
		DataUser user = dataUsers.getFromGoogleID(ID); 
		if(user == null) {		// If the user does not exist create it
			String username;
			do {
				username = generateRndString(15);						// Generate a username
			} while(dataUsers.isUsernameUsed(username));				// and make sure it is unique
			user = dataUsers.createUser(username, email, ID, null, null, "");	// Then create the user
			if(user == null) {	// If the user is still does not exist return null
				return null;
			}
		}
		
		// Add token and return
		dataUsers.addToken(user.getUsername(), token, tokenExp);
		return new UsernameTokenPair(user.getUsername(), token, tokenExp);
	}

	/**
	 * Checks if the user is properly authenticated
	 * 
	 * @param username	username to check for
	 * @param token		token to check for
	 * @return			if the user is authenticated
	 */
	public static boolean isLoggedIn(String username, String token) {
		return WeatherWarningApplication.data.getUsers().isTokenValid(username, token);		// Just send to data handler
	}
	
	/**
	 * Logs a user in
	 * 
	 * @param identifier		the user's username or email
	 * @param password			the user's password
	 * @return					{@code UsernameTokenPair} that contains the username, token, and token expiration
	 * @throws BadRequestError	When the identifier can't be used or the password is wrong
	 */
	public static UsernameTokenPair login(String identifier, String password) throws BadRequestError {
		IDataUsers dataUsers = WeatherWarningApplication.data.getUsers();	// Make data easier to access
		DataUser user = dataUsers.getUser(identifier);
		if(user == null) {
			user = dataUsers.getFromEmail(identifier);
			if(user == null) {
				throw new BadRequestError("Username or password is wrong.");
			}
		}
		
		String hashedPassword = Base64.getEncoder().encodeToString(hashPassword(password, user.getSalt()));
		if(!user.getPassword().equals(hashedPassword)) {
			throw new BadRequestError("Username or password is wrong.");
		}
		
		// Generate token and expiration date/time
		Date tokenExp = Date.from(Instant.now().plus(Duration.ofDays(EXPIRATION_DAYS)));
		String token;
		do {
			token = generateRndString(50);
		}while(dataUsers.isTokenUsed(token));	// Make sure this is a unique token
		
		// Add token and return
		dataUsers.addToken(user.getUsername(), token, tokenExp);
		return new UsernameTokenPair(user.getUsername(), token, tokenExp);
	}
	
	/**
	 * Logs out the user
	 * 
	 * @param username	the username to logout from
	 * @param token		the token being used
	 */
	public static void logout(String username, String token) {
		WeatherWarningApplication.data.getUsers().removeToken(username, token);		// Just send to data handler
	}
	
	/**
	 * Used to return the username, token, and token expiration when someone logs in
	 * Only has the constructor and getters
	 */
	public static class UsernameTokenPair{
		private final String username;
		private final String token;
		private final Date tokenExp;
		
		public UsernameTokenPair(String username, String token, Date tokenExp) {
			this.username = username;
			this.token = token;
			this.tokenExp = tokenExp;
		}

		public String getUsername() {
			return username;
		}

		public String getToken() {
			return token;
		}

		public Date getTokenExp() {
			return tokenExp;
		}				
	}
}
