package utd.group12.weatherwarning.data;

import java.util.Date;

import javax.annotation.Nullable;

/**
 * Interface for the class that handles all user related data 
 */
public interface IDataUsers {
	/**
	 * Adds a token to the user
	 * 
	 * @param username		username to add the token to
	 * @param token			token to add
	 * @param tokenExp		the expiration date for the token
	 */
	public void addToken(String username, String token, Date tokenExp);
	
	/**
	 * Checks if the token is already used
	 * 
	 * @param token		token to check
	 * @return			if the token is already used
	 */
	public boolean isTokenUsed(String token);
	
	/**
	 * Checks if a token is valid for the user
	 * 
	 * @param username	the username for the token
	 * @param token		the token for the username
	 * @return			if the token is valid for the username
	 */
	public boolean isTokenValid(String username, String token);

	
	
	/**
	 * Creates a new user <br/>
	 * 
	 * Only {@code googleID} or {@code password} should be used NOT both 
	 * 
	 * @param username		the new user's username
	 * @param email			the new user's email
	 * @param googleID		the new user's googleID (or use {@code password})
	 * @param password		the new user's password (or use {@code googleID})
	 * @param phoneNumber	the new user's phone number
	 * @return				the user created
	 */
	@Nullable
	public DataUser createUser(String username, String email, String googleID, String password, String phoneNumber);
	
	/**
	 * Gets a user by their Google ID
	 * 
	 * @param ID	the google ID to use
	 * @return		the user
	 */
	@Nullable
	public DataUser getFromGoogleID(String ID);
	
	/**
	 * Gets the user from their username
	 * 
	 * @param username	the username to use
	 * @return			the user
	 */
	@Nullable
	public DataUser getUser(String username);
	
	/**
	 * Checks if a user with a given username exits
	 * 
	 * @param username	the username to check for
	 * @return			if there is a user with that username
	 */
	public boolean isUsernameUsed(String username);
}
