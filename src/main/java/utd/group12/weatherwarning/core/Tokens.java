package utd.group12.weatherwarning.core;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import utd.group12.weatherwarning.Utils;
import utd.group12.weatherwarning.data.DataToken;
import utd.group12.weatherwarning.data.IDataTokens;
import utd.group12.weatherwarning.errors.NotFoundError;

public class Tokens {
	private final static int EXPIRATION_DAYS = 30;			// The number of days each token should stay valid for
	
	private final Core core;
	private IDataTokens data;
	
	public Tokens(Core core) {
		this.core = core;
	}
	
	/**
	 * When everything is starting up
	 */
	void start() {
		this.data = core.data.getTokens();
	}
	
	/**
	 * Creates the token
	 * 
	 * @param username			the username to create the token for
	 * @return					the token
	 * @throws NotFoundError	if the user does not exist
	 */
	public DataToken create(String username) throws NotFoundError {
		if(!this.core.users.exists(username)) {
			throw new NotFoundError("User does not exist");
		}
		
		Date expiration = Date.from(Instant.now().plus(Duration.ofDays(EXPIRATION_DAYS)));	// Create the token
		String tokenStr;
		do {
			tokenStr = Utils.generateRndString(50);
		}while(this.data.exists(tokenStr));	// Make sure this is a unique token
		
		DataToken token = new DataToken(tokenStr, expiration);
		// Add token and return
		this.data.add(username.toLowerCase(), token);
		return token;
	}
	
	/**
	 * Checks if the token exists
	 * 
	 * @param username	the username to check for
	 * @param token		the token to check for
	 * @return			if the username exits for the user
	 */
	public boolean exists(String username, String token) {
		return this.data.exists(username.toLowerCase(), token);		// Just send to data handler
	}
	
	/**
	 * Removes a token
	 * 
	 * @param username			the username to remove the token from 
	 * @param token				the token to remove
	 * @throws NotFoundError	if the user does not exist
	 */
	public void remove(String username, String token) throws NotFoundError {
		if(!this.core.users.exists(username)) {
			throw new NotFoundError("User does not exist");
		}
		this.data.remove(username.toLowerCase(), token);		// Just send to data handler
	}
}
