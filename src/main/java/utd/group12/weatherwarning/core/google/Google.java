package utd.group12.weatherwarning.core.google;

import utd.group12.weatherwarning.core.Core;
import utd.group12.weatherwarning.data.IDataServer;

public class Google {
	final Core core;
	final IDataServer data;
	
	public final GoogleLogin login; 

	public Google(Core core, IDataServer data) {
		this.core = core;
		this.data = data;
		this.login = new GoogleLogin(this);
	}
	
	/**
	 * Gets the google API key
	 * 
	 * @return	the google API key
	 */
	public String getAPIKey() {
		return this.data.getInfo().getGoogleAPIKey();
	}

	public String getServerGoogleClientID() {
		return this.data.getInfo().getClientID();
	}
}
