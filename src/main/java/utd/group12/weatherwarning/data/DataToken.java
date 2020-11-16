package utd.group12.weatherwarning.data;

import java.util.Date;

/**
 * The class used for the tokens
 */
public class DataToken {
	private final String token;
	private final Date expiration;
	
	/**
	 * Constructor for the token object <br/>
	 * 
	 * @param token			the token
	 * @param expiration	the token's expiration
	 */
	public DataToken(String token, Date expiration) {
		this.token = token;
		this.expiration = expiration;
	}

	/**
	 * Gets the token
	 * 
	 * @return	the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Gets the token's expiration
	 * 
	 * @return	the token's expiration
	 */
	public Date getExpiration() {
		return expiration;
	}
}
