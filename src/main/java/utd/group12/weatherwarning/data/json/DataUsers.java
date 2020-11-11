package utd.group12.weatherwarning.data.json;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import utd.group12.weatherwarning.WeatherWarningApplication;
import utd.group12.weatherwarning.data.DataUser;
import utd.group12.weatherwarning.data.IDataUsers;

public class DataUsers implements IDataUsers{
	Map<String, JsonUser> users = new HashMap<String, JsonUser>();

	/**
	 * Converts a {@code JsonUser} to a {@code DataUser}
	 * @param jUser		the {@code JsonUser} to convert
	 * @return			the {@code DataUser}
	 */
	@Nullable
	private static DataUser toDataUser(JsonUser jUser) {
		if(jUser == null) {	// Return null if the JSON user is null
			return null;
		}
		return new DataUser(jUser.username, jUser.email, jUser.google_id, jUser.password, jUser.phoneNumber);	// Convert the user
	}
	
	/**
	 * Adds a token to the user
	 * 
	 * @param username		username to add the token to
	 * @param token			token to add
	 * @param tokenExp		the expiration date for the token
	 */
	@Override
	public void addToken(String username, String token, Date tokenExp) {
		if(users.get(username) == null) {	// if the user doesn't exist return
			return;
		}
		// remove old tokens
		Iterator<Entry<String, Date>> it = users.get(username).tokens.entrySet().iterator();
		while(it.hasNext()) {								// Loop through the map 
			Entry<String, Date> usrToken = it.next();
			if(usrToken.getValue().before(Date.from(Instant.now()))) {	// If the expiration is before today remove it
				it.remove();
			}
		}
		// if there are too many tokens, remove one (should only happen if someone is overloading the program)
		if(users.get(username).tokens.size() > 100) {
			users.get(username).tokens.remove(users.get(username).tokens.keySet().iterator().next());
		}
		users.get(username).tokens.put(token, tokenExp); // Actually add the token and save
		WeatherWarningApplication.data.forceSave();
	}
	/**
	 * Checks if the token is already used
	 * 
	 * @param token		token to check
	 * @return			if the token is already used
	 */
	@Override
	public boolean isTokenUsed(String token) {
		for(JsonUser user : users.values()) {				// Loop through all the users
			for(String userToken : user.tokens.keySet()) {	// and all the tokens
				if(userToken.equals(token)) {				// if we find our token return true
					return true;
				}
			}
		}
		return false;	// If it gets here, our token was not found
	}

	/**
	 * Checks if a token is valid for the user
	 * 
	 * @param username	the username for the token
	 * @param token		the token for the username
	 * @return			if the token is valid for the username
	 */
	@Override
	public boolean isTokenValid(String username, String token) {
		if(!users.containsKey(username)) {	// If the user does not exist token is not valid
			return false;
		}
		
		Date experation = users.get(username).tokens.get(token);
		if(experation == null || experation.before(Date.from(Instant.now()))) {	// If the token doesn't exit for the user  
			return false;														// or is expired the token is not valid
		}
		
		return true; // Else token is valid
	}
	
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
	@Override
	@Nullable
	public DataUser createUser(String username, String email, String googleID, String password, String phoneNumber) {
		if(users.containsKey(username)) {	// If the user exits return null
			return null;
		}
		users.put(username, new JsonUser(username, email, googleID, password, phoneNumber)); // Else add them and save
		WeatherWarningApplication.data.forceSave();
		return toDataUser(users.get(username));	// Then return the new user
	}
	
	/**
	 * Gets a user by their Google ID
	 * 
	 * @param ID	the google ID to use
	 * @return		the user
	 */
	@Override
	@Nullable
	public DataUser getFromGoogleID(String ID) {
		for(JsonUser user : users.values()) {	// Loop through the users
			if(user.google_id.equals(ID)) {		// And return the user if it has the right google ID
				return toDataUser(user);
			}
		}
		return null;
	}

	@Override
	@Nullable
	public DataUser getUser(String username) {
		return toDataUser(users.get(username));
	}

	/**
	 * Checks if a user with a given username exits
	 * 
	 * @param username	the username to check for
	 * @return			if there is a user with that username
	 */
	@Override
	public boolean isUsernameUsed(String username) {
		return users.containsKey(username);
	}
	
	/**
	 * Stores the user in the JSON
	 */
	private class JsonUser{
		String username;
		String email;
		String google_id;
		String password;
		String phoneNumber;
		Map<String, Date> tokens = new HashMap<String, Date>();
		
		/**
		 * Creates the {@code JsonUser}
		 * 
		 * @param username		the username
		 * @param email			the email
		 * @param google_id		the googleID
		 * @param password		the password
		 * @param phoneNumber	the phone number
		 */
		public JsonUser(String username, String email, String google_id, String password, String phoneNumber) {
			this.username = username;
			this.email = email;
			this.google_id = google_id;
			this.password = password;
			this.phoneNumber = phoneNumber;
		}
	}
}
