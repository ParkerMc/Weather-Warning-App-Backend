package utd.group12.weatherwarning.response;

/**
 * The response for the info endpoint that holds any random information the client may need
 */
public class InfoResponse {
	private final String googleLoginUrl;
	
	/**
	 * Creates the info response
	 * 
	 * @param googleLoginUrl	The URL to have to user redirected to in order to log in with google
	 */
	public InfoResponse(String googleLoginUrl) {
		this.googleLoginUrl = googleLoginUrl;
	}

	public String getGoogleLoginUrl() {
		return this.googleLoginUrl;
	}
}
