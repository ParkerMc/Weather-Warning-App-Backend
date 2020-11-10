package utd.group12.weatherwarning.data.json;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import utd.group12.weatherwarning.WeatherWarningApplication;
import utd.group12.weatherwarning.data.DataUser;
import utd.group12.weatherwarning.data.IDataUsers;

public class DataUsers implements IDataUsers{
	Map<String, JsonUser> users = new HashMap<String, JsonUser>();

	private static DataUser toDataUser(JsonUser jUser) {
		if(jUser == null) {
			return null;
		}
		return new DataUser(jUser.username, jUser.email, jUser.googleId, jUser.password, jUser.phoneNumber);
	}
	
	@Override
	public void addToken(String username, String token, Date tokenExp) {
		if(users.get(username) == null) {
			return;
		}
		// remove old
		Iterator<Entry<String, Date>> it = users.get(username).tokens.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, Date> usrToken = it.next();
			if(usrToken.getValue().before(Date.from(Instant.now()))) {
				it.remove();
			}
		}
		// remove one if there are too many (should only happen if someone is overloading the program)
		if(users.get(username).tokens.size() > 100) {
			users.get(username).tokens.remove(users.get(username).tokens.keySet().iterator().next());
		}
		users.get(username).tokens.put(token, tokenExp);
		WeatherWarningApplication.data.forceSave();
	}
	
	@Override
	public DataUser getFromGoogleID(String ID) {
		for(JsonUser user : users.values()) {
			if(user.googleId.equals(ID)) {
				return toDataUser(user);
			}
		}
		return null;
	}
	
	@Override
	public boolean isTokenUsed(String token) {
		for(JsonUser user : users.values()) {
			for(String userToken : user.tokens.keySet()) {
				if(userToken.equals(token)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public DataUser createUser(String username, String email, String googleID, String password, String phoneNumber) {
		if(users.containsKey(username)) {
			return null;
		}
		users.put(username, new JsonUser(username, email, googleID, password, phoneNumber));
		WeatherWarningApplication.data.forceSave();
		return toDataUser(users.get(username));
	}

	@Override
	public boolean isUsernameUsed(String username) {
		return users.containsKey(username);
	}
	
	@Override
	public DataUser getUser(String username) {
		return toDataUser(users.get(username));
	}
	
	@Override
	public boolean isTokenValid(String username, String token) {
		if(!users.containsKey(username)) {
			return false;
		}
		Date experation = users.get(username).tokens.get(token);
		if(experation == null || experation.before(Date.from(Instant.now()))) {
			return false;
		}
		return true;
	}
	
	private class JsonUser{
		String username;
		String email;
		String googleId;
		String password;
		String phoneNumber;
		Map<String, Date> tokens = new HashMap<String, Date>();
		
		public JsonUser(String username, String email, String googleId, String password, String phoneNumber) {
			this.username = username;
			this.email = email;
			this.googleId = googleId;
			this.password = password;
			this.phoneNumber = phoneNumber;
		}
	}
}
