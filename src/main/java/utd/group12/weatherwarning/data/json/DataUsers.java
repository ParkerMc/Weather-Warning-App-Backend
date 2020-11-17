package utd.group12.weatherwarning.data.json;

import java.util.HashMap;
import java.util.Map;

import utd.group12.weatherwarning.core.Core;
import utd.group12.weatherwarning.data.DataUser;
import utd.group12.weatherwarning.data.IDataUsers;
import utd.group12.weatherwarning.errors.ConflictError;
import utd.group12.weatherwarning.errors.NotFoundError;

public class DataUsers implements IDataUsers{
	Map<String, JsonUser> users = new HashMap<String, JsonUser>();

	/**
	 * Converts a {@code JsonUser} to a {@code DataUser}
	 * @param jUser		the {@code JsonUser} to convert
	 * @return			the {@code DataUser}
	 */
	private static DataUser toDataUser(JsonUser jUser) {
		return new DataUser(jUser.username, jUser.email, jUser.google_id, jUser.password, jUser.salt, jUser.name, jUser.phoneNumber);	// Convert the user
	}
	
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
	 * @param name				the user's name
	 * @param phoneNumber		the new user's phone number
	 * @return					the user created
	 * @throws ConflictError	if the username(key) is already used
	 */
	@Override
	public DataUser create(String username, String email, String googleID, String password, String salt, String name, String phoneNumber) throws ConflictError {
		if(users.containsKey(username)) {	// If the username is already used thorw error
			throw new ConflictError("Username already used.");
		}
		users.put(username, new JsonUser(username, email, googleID, password, salt, name, phoneNumber)); // Else add them and save
		Core.instance.forceSave();
		return toDataUser(users.get(username));	// Then return the new user
	}
	
	/**
	 * Gets a user by their email 
	 * 
	 * @param email				the email to use
	 * @return					the user
	 * @throws NotFoundError 	if the user is not found
	 */
	@Override
	public DataUser getFromEmail(String email) throws NotFoundError {
		for(JsonUser user : users.values()) {			// Loop through all the users
			if(user.email.equals(email)) {				// if we find our email return true
				return toDataUser(user);
			}
		}
		throw new NotFoundError("User not found.");
	}
	
	/**
	 * Gets a user by their Google ID
	 * 
	 * @param ID				the google ID to use
	 * @return					the user
	 * @throws NotFoundError 	if the user is not found
	 */
	@Override
	public DataUser getFromGoogleID(String ID) throws NotFoundError {
		for(JsonUser user : users.values()) {	// Loop through the users
			if(user.google_id.equals(ID)) {		// And return the user if it has the right google ID
				return toDataUser(user);
			}
		}
		throw new NotFoundError("User not found.");
	}

	/**
	 * Gets the user from their username
	 * 
	 * @param username			the username to use
	 * @return					the user
	 * @throws NotFoundError 	if the user is not found
	 */
	@Override
	public DataUser get(String username) throws NotFoundError {
		JsonUser user = users.get(username);
		if(user == null) {
			throw new NotFoundError("User not found.");
		}
		return toDataUser(user);
	}
	
	/**
	 * Checks if a user with a given email exits
	 * 
	 * @param email		the email to check for
	 * @return			if there is a user with that email
	 */
	@Override
	public boolean isEmailUsed(String email) {
		for(JsonUser user : users.values()) {			// Loop through all the users
			if(user.email.equals(email)) {				// if we find our email return true
				return true;
			}
		}
		return false;	// If it gets here, our email was not found
	}
	
	/**
	 * Checks if a user with a given username exits
	 * 
	 * @param username	the username to check for
	 * @return			if there is a user with that username
	 */
	@Override
	public boolean exists(String username) {
		return users.containsKey(username);
	}
	
	/**
	 * Updates an already created user
	 * 
	 * @param username	the username to modify
	 * @param email				the new email
	 * @param password			the new password
	 * @param name				the new name
	 * @param phoneNumber		the new phoneNumber
	 * @throws NotFoundError	if the user does not exist
	 */
	@Override
	public void update(String username, String email, String newPassword, String newSalt, String name,
			String phoneNumber) throws NotFoundError {
		JsonUser user = users.get(username);
		if(user == null) {
			throw new NotFoundError("User not found");
		}
		if(email != null) {
			user.email = email;
		}
		if(newPassword != null) {
			user.password = newPassword;
		}
		if(newSalt != null) {
			user.salt = newSalt;
		}
		if(name != null) {
			user.name = name;
		}
		if(phoneNumber != null) {
			user.phoneNumber = phoneNumber;
		}
		Core.instance.forceSave();
	}
	
	/**
	 * Stores the user in the JSON
	 */
	private class JsonUser{
		String username;
		String email;
		String google_id;
		String password;
		String salt;
		String name;
		String phoneNumber;
		
		/**
		 * Creates the {@code JsonUser}
		 * 
		 * @param username		the username
		 * @param email			the email
		 * @param google_id		the googleID
		 * @param password		the password
		 * @param salt			the password salt
		 * @param phoneNumber	the phone number
		 */
		public JsonUser(String username, String email, String google_id, String password, String salt, String name, String phoneNumber) {
			this.username = username;
			this.email = email;
			this.google_id = google_id;
			this.password = password;
			this.salt = salt;
			this.name = name;
			this.phoneNumber = phoneNumber;
		}
	}
}
