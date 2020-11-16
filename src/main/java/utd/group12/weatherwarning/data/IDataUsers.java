package utd.group12.weatherwarning.data;

import utd.group12.weatherwarning.errors.ConflictError;
import utd.group12.weatherwarning.errors.NotFoundError;

/**
 * Interface for the class that handles all user related data 
 */
public interface IDataUsers {
	/**
	 * Creates a new user <br/>
	 * 
	 * Only {@code googleID} or {@code password} should be used NOT both 
	 * 
	 * @param username			the new user's username
	 * @param email				the new user's email
	 * @param googleID			the new user's googleID (or use {@code password})
	 * @param password			the new user's password (or use {@code googleID})
	 * @param salt				the salt for the new user's password (or use {@code googleID})
	 * @param phoneNumber		the new user's phone number
	 * @return					the user created
	 * @throws ConflictError	if the username(key) is already used
	 */
	public DataUser create(String username, String email, String googleID, String password, String salt, String phoneNumber) throws ConflictError;
	
	/**
	 * Gets a user by their email 
	 * 
	 * @param email				the email to use
	 * @return					the user
	 * @throws NotFoundError 	if the user is not found
	 */
	public DataUser getFromEmail(String email) throws NotFoundError;
	
	/**
	 * Gets a user by their Google ID
	 * 
	 * @param ID				the google ID to use
	 * @return					the user
	 * @throws NotFoundError 	if the user is not found
	 */
	public DataUser getFromGoogleID(String ID) throws NotFoundError;
	
	/**
	 * Gets the user from their username
	 * 
	 * @param username	the username to use
	 * @return			the user
	 * @throws NotFoundError 	if the user is not found
	 */
	public DataUser get(String username) throws NotFoundError;
	
	/**
	 * Checks if a user with a given email exits
	 * 
	 * @param email		the email to check for
	 * @return			if there is a user with that email
	 */
	public boolean isEmailUsed(String email);
	
	/**
	 * Checks if a user with a given username exists
	 * 
	 * @param username	the username to check for
	 * @return			if there is a user with that username
	 */
	public boolean exists(String username);
}
