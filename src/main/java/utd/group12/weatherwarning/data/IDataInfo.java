package utd.group12.weatherwarning.data;

/**
 * Interface for the class that handles the random info
 */
public interface IDataInfo {
	/**
	 * Gets the client ID for Google
	 * 
	 * @return the client ID
	 */
	public String getClientID();
	
	/**
	 * Gets the client secret for Google
	 * 
	 * @return the client secret
	 */
	public String getClientSecret();
	
	/**
	 * Gets the Google API key
	 * 
	 * @return	the Google API key
	 */
	public String getGoogleAPIKey();
	
	/**
	 * Gets the redirect URI for after OAuth
	 * 
	 * @return the redirect URI
	 */
	public String getRedirectURI();
}
