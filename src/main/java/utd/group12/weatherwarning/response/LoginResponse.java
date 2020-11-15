package utd.group12.weatherwarning.response;

import java.util.Date;

import utd.group12.weatherwarning.core.UserLogin.UsernameTokenPair;

/**
 * The response for when a login is successful<br/>
 * Includes the username, token and token expiration 
 */
public class LoginResponse {
	private final String username;
	private final String token;
	private final Date expiration;
	
	/**
	 * Creates the login response
	 * 
	 * @param username		The [now] logged in username
	 * @param token			The token just created
	 * @param expiration	The token's expiration date
	 */
	public LoginResponse(String username, String token, Date expiration) {
		this.username = username;
		this.token = token;
		this.expiration = expiration;
	}

	/**
	 * Creates the login response
	 * 
	 * @param usernameTokenPair		The username, token, and token expiration 
	 */
	public LoginResponse(UsernameTokenPair usernameTokenPair) {
		this(usernameTokenPair.getUsername(), usernameTokenPair.getToken(), usernameTokenPair.getTokenExp());
	}

	public long getExpiration() {
		return this.expiration.getTime() / 1000L;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public String getUsername() {
		return this.username;
	}
}
