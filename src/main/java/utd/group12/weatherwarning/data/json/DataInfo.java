package utd.group12.weatherwarning.data.json;

import utd.group12.weatherwarning.data.IDataInfo;

public class DataInfo implements IDataInfo {
	private String clientID = "";
	private String authURI = "";
	private String clientSecret = "";
	private String redirectURI = "";

	@Override
	public String getClientID() {
		return this.clientID;
	}

	@Override
	public String getAuthURI() {
		return this.authURI;
	}

	@Override
	public String getClientSecret() {
		return this.clientSecret;
	}

	@Override
	public String getRedirectURI() {
		return this.redirectURI;
	}

}
