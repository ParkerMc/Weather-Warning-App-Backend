package utd.group12.weatherwarning.google;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import utd.group12.weatherwarning.Utils;
import utd.group12.weatherwarning.WeatherWarningApplication;
import utd.group12.weatherwarning.data.IDataInfo;

public class GoogleLogin {
	private final static String SCOPE = "https://www.googleapis.com/auth/userinfo.email";
	
	public static String getLoginUrl() {
		IDataInfo dataInfo = WeatherWarningApplication.data.getInfo();
		return String.format("https://accounts.google.com/o/oauth2/auth?client_id=%s&response_type=code&scope=%s&redirect_uri=%s&access_type=offline",
				dataInfo.getClientID(), SCOPE, dataInfo.getRedirectURI());
	}
	
	public static UserInfoResponce getUserInfo(String code) throws IOException {
		IDataInfo dataInfo = WeatherWarningApplication.data.getInfo();		
		// Exchange auth code for access token
		TokenResponce token = Utils.POSTRequest("https://oauth2.googleapis.com/token", new TokenRequest(dataInfo.getClientID(), dataInfo.getClientSecret(), code, "authorization_code", dataInfo.getRedirectURI()), TokenResponce.class);
		Map<String, String> args = new HashMap<String, String>();
		if(token.access_token == null) {
			return null;
		}
		args.put("access_token", token.access_token);
		UserInfoResponce userInfo = Utils.GETRequest("https://www.googleapis.com/oauth2/v2/userinfo", args, UserInfoResponce.class);
		return userInfo;
		
	}
	
	@SuppressWarnings("unused")
	private static class TokenRequest{
		String client_id;
		String client_secret;
		String code;
		String grant_type;
		String redirect_uri;
		
		TokenRequest(String client_id, String client_secret, String code, String grant_type, String redirect_uri){
			this.client_id = client_id;
			this.client_secret = client_secret;
			this.code = code;
			this.grant_type = grant_type;
			this.redirect_uri = redirect_uri;
		}
	}
	
	@SuppressWarnings("unused")
	private static class TokenResponce{
		String access_token;
		int expires_in;
		String refresh_token;
		String scope;
		String token_type;
	}
	
	public static class UserInfoResponce{
		String id;
		String email;
		boolean verified_email;
		
		public String getId() {
			return id;
		}
		
		public String getEmail() {
			return email;
		}
		
		public boolean isVerifiedEmail() {
			return verified_email;
		}
	}
}
