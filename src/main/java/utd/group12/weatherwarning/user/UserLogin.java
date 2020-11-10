package utd.group12.weatherwarning.user;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import utd.group12.weatherwarning.WeatherWarningApplication;
import utd.group12.weatherwarning.data.DataUser;
import utd.group12.weatherwarning.data.IDataUsers;

public class UserLogin {
	private final static int EXPERATION_DAYS = 30;
	
	private static String generateRndString(int len) {
        String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder str = new StringBuilder();
        Random rnd = new Random();
        while (str.length() < len) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            str.append(CHARS.charAt(index));
        }
        return str.toString();

    }

	// Returns token
	public static UsernameTokenPair GoogleLogin(String ID, String email) {
		IDataUsers dataUsers = WeatherWarningApplication.data.getUsers();
		Date tokenExp = Date.from(Instant.now().plus(Duration.ofDays(EXPERATION_DAYS)));
		String token;
		do {
			token = generateRndString(50);
		}while(dataUsers.isTokenUsed(token));
		
		DataUser user = dataUsers.getFromGoogleID(ID);
		if(user == null) {
			String username;
			do {
				username = generateRndString(15);
			} while(dataUsers.isUsernameUsed(username));
			user = dataUsers.createUser(username, email, ID, "", "");
		}
		if(user == null) {
			return null;
		}
		dataUsers.addToken(user.getUsername(), token, tokenExp);
		return new UsernameTokenPair(user.getUsername(), token, tokenExp);
	}
	
	public static class UsernameTokenPair{
		private final String username;
		private final String token;
		private final Date tokenExp;
		
		public UsernameTokenPair(String username, String token, Date tokenExp) {
			super();
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

	public static boolean isLoggedIn(String username, String token) {
		return WeatherWarningApplication.data.getUsers().isTokenValid(username, token);
	}
}
