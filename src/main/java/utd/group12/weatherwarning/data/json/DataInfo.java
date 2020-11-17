package utd.group12.weatherwarning.data.json;

import utd.group12.weatherwarning.data.IDataInfo;

/**
 * Handles loading the random data info from the JSON
 */
public class DataInfo implements IDataInfo {
	private String client_id = "";
	private String client_secret = "";
	private String redirect_uri = "";
	private String google_api_key = "";

	/**
	 * Gets the client ID for Google
	 * 
	 * @return the client ID
	 */
	@Override
	public String getClientID() {
		return this.client_id;
	}

	/**
	 * Gets the client secret for Google
	 * 
	 * @return the client secret
	 */
	@Override
	public String getClientSecret() {
		return this.client_secret;
	}
	
	/**
	 * Gets the Google API key
	 * 
	 * @return	the Google API key
	 */
	@Override
	public String getGoogleAPIKey() {
		return this.google_api_key;
	}

	/**
	 * Gets the redirect URI for after OAuth
	 * 
	 * @return the redirect URI
	 */
	@Override
	public String getRedirectURI() {
		return this.redirect_uri;
	}
}
