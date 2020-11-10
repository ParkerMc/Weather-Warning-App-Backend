package utd.group12.weatherwarning.response;

import java.util.Date;

public class LoginResponse {
	private final String username;
	private final String token;
	private final Date expiration;
	
	public LoginResponse(String username, String token, Date expiration) {
		this.username = username;
		this.token = token;
		this.expiration = expiration;
	}

	public String getUsername() {
		return this.username;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public long getExpiration() {
		return this.expiration.getTime() / 1000L;
	}
}
