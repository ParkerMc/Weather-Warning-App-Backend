package utd.group12.weatherwarning.google;

import utd.group12.weatherwarning.WeatherWarningApplication;
import utd.group12.weatherwarning.data.IDataInfo;

public class GoogleLogin {
	final static String scope = "https://www.googleapis.com/auth/userinfo.email";
	
	public static String getLoginUrl() {
		IDataInfo dataInfo = WeatherWarningApplication.data.getInfo();
		return String.format("%s?client_id=%s&response_type=code&scope=%s&redirect_uri=%s&access_type=offline",
				dataInfo.getAuthURI(), dataInfo.getClientID(), scope, dataInfo.getRedirectURI());
	}
}
