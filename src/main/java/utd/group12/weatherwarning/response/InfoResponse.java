package utd.group12.weatherwarning.response;

/**
 * The response for the info endpoint that holds any random information the client may need
 */
public class InfoResponse {
	private final String googleLoginUrl;
	private final String googleAPIKey;
	
	/**
	 * Creates the info response
	 * 
	 * @param googleLoginUrl	The URL to have to user redirected to in order to log in with google
	 * @param googleAPIKey		The google API key
	 */
	public InfoResponse(String googleLoginUrl, String googleAPIKey) {
		this.googleLoginUrl = googleLoginUrl;
		this.googleAPIKey = googleAPIKey;
	}

	public String getGoogleAPIKey() {
		return googleAPIKey;
	}
	
	public String getGoogleLoginUrl() {
		return this.googleLoginUrl;
	}
}
