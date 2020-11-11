package utd.group12.weatherwarning.google;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import utd.group12.weatherwarning.Utils;
import utd.group12.weatherwarning.WeatherWarningApplication;
import utd.group12.weatherwarning.data.IDataInfo;

/**
 * Handles everything related to logging in with google
 */
public class GoogleLogin {
	private final static String SCOPE = "https://www.googleapis.com/auth/userinfo.email";	// The scope of info needed
	
	/**
	 * Generates the URL for the user to login at
	 *  
	 * @return	the URL for the user to login with Google
	 */
	public static String getLoginUrl() {
		IDataInfo dataInfo = WeatherWarningApplication.data.getInfo(); // Make data easier to access
		
		// Format the string
		return String.format("https://accounts.google.com/o/oauth2/auth?client_id=%s&response_type=code&scope=%s&redirect_uri=%s&access_type=offline",
				dataInfo.getClientID(), SCOPE, dataInfo.getRedirectURI());
	}
	
	/**
	 * Gets the requested user's info from the auth code
	 * 
	 * @param code			user's auth code from callback
	 * @return				the user info
	 * @throws IOException	if there is an http issue
	 */
	@Nullable
	public static UserInfoResponce getUserInfo(String code) throws IOException {
		IDataInfo dataInfo = WeatherWarningApplication.data.getInfo();	// Make data easier to access
		
		// Exchange auth code for access token
		TokenResponce token = Utils.POSTRequest("https://oauth2.googleapis.com/token", new TokenRequest(dataInfo.getClientID(), dataInfo.getClientSecret(), code, "authorization_code", dataInfo.getRedirectURI()), TokenResponce.class);
		
		// Request the user info from Google
		Map<String, String> args = new HashMap<String, String>();	// Create the map to hold the args 
		if(token.access_token == null) {		// If we don't have an access token return null
			return null;
		}
		args.put("access_token", token.access_token);	// Add arg and perform request
		UserInfoResponce userInfo = Utils.GETRequest("https://www.googleapis.com/oauth2/v2/userinfo", args, UserInfoResponce.class);
		return userInfo;
		
	}
	
	/**
	 * Used to format the post data sent when exchanging the code for a token
	 */
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
	
	/**
	 * Used to parse the response when exchanging the code for a token
	 */
	@SuppressWarnings("unused")
	private static class TokenResponce{
		String access_token;
		int expires_in;
		String refresh_token;
		String scope;
		String token_type;
	}
	
	/**
	 * Used to parse the response for user info from google 
	 */
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