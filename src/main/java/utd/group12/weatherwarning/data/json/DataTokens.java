package utd.group12.weatherwarning.data.json;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import utd.group12.weatherwarning.core.Core;
import utd.group12.weatherwarning.data.DataToken;
import utd.group12.weatherwarning.data.IDataTokens;

public class DataTokens implements IDataTokens{
	Map<String, Map<String, Date>> tokens = new HashMap<String, Map<String, Date>>();

	/**
	 * Adds a token to the user
	 * 
	 * @param username			username to add the token to
	 * @param token				token to add
	 */
	@Override
	public void add(String username, DataToken token) {
		if(!tokens.containsKey(username)) {	// if the user doesn't exist throw error
			tokens.put(username, new HashMap<String, Date>());
		}
		// remove old tokens
		Iterator<Entry<String, Date>> it = tokens.get(username).entrySet().iterator();
		while(it.hasNext()) {								// Loop through the map 
			Entry<String, Date> usrToken = it.next();
			if(usrToken.getValue().before(Date.from(Instant.now()))) {	// If the expiration is before today remove it
				it.remove();
			}
		}
		// if there are too many tokens, remove one (should only happen if someone is overloading the program)
		if(tokens.get(username).size() > 100) {
			tokens.get(username).remove(tokens.get(username).keySet().iterator().next());
		}
		tokens.get(username).put(token.getToken(), token.getExpiration()); // Actually add the token and save
		Core.instance.forceSave();
	}
	/**
	 * Checks if the token is already used
	 * 
	 * @param token		token to check
	 * @return			if the token is already used
	 */
	@Override
	public boolean exists(String token) {
		for(Map<String, Date> user : tokens.values()) {				// Loop through all the users
			for(String userToken : user.keySet()) {	// and all the tokens
				if(userToken.equals(token)) {				// if we find our token return true
					return true;
				}
			}
		}
		return false;	// If it gets here, our token was not found
	}

	/**
	 * Checks if a token exists for the user
	 * 
	 * @param username	the username for the token
	 * @param token		the token for the username
	 * @return			if the token is valid for the username
	 */
	@Override
	public boolean exists(String username, String token) {
		if(!tokens.containsKey(username)) {	// If the user does not exist token is not valid
			return false;
		}
		
		Date experation = tokens.get(username).get(token);
		if(experation == null || experation.before(Date.from(Instant.now()))) {	// If the token doesn't exit for the user  
			return false;														// or is expired the token is not valid
		}
		
		return true; // Else token is valid
	}
	
	/**
	 * Removes the token from a user
	 * 
	 * @param username	the username for the user
	 * @param token		the token to remove
	 */
	@Override
	public void remove(String username, String token) {
		if(!tokens.containsKey(username)) {
			return;
		}
		tokens.get(username).remove(token);
		Core.instance.forceSave();
	}
}
