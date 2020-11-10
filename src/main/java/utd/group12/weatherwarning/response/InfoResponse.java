package utd.group12.weatherwarning.response;

public class InfoResponse {
	private final String googleLoginUrl;
	
	public InfoResponse(String googleLoginUrl) {
		this.googleLoginUrl = googleLoginUrl;
	}

	public String getGoogleLoginUrl() {
		return this.googleLoginUrl;
	}
}
