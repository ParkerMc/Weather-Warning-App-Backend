package utd.group12.weatherwarning.user;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import javax.annotation.Nullable;

import utd.group12.weatherwarning.WeatherWarningApplication;
import utd.group12.weatherwarning.data.DataUser;
import utd.group12.weatherwarning.data.IDataUsers;

/**
 * Handles processing user login and account creation
 */
public class UserLogin {
	private final static int EXPIRATION_DAYS = 30;		// The number of days each token should stay valid for
	
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
			user = dataUsers.createUser(username, email, ID, "", "");	// Then create the user
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
	 * @param username	username to check for
	 * @param token		token to check for
	 * @return			if the user is authenticated
	 */
	public static boolean isLoggedIn(String username, String token) {
		return WeatherWarningApplication.data.getUsers().isTokenValid(username, token);		// Just send to data handler
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
