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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expiration == null) ? 0 : expiration.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataToken other = (DataToken) obj;
		if (expiration == null) {
			if (other.expiration != null)
				return false;
		} else if (!expiration.equals(other.expiration))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
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
