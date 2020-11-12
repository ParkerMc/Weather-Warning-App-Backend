/**
 * Returns all the random info to the client
 */
package utd.group12.weatherwarning.controller;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utd.group12.weatherwarning.WeatherWarningApplication;
import utd.group12.weatherwarning.data.DataUser;
import utd.group12.weatherwarning.google.GoogleLogin;
import utd.group12.weatherwarning.google.GoogleLogin.UserInfoResponce;
import utd.group12.weatherwarning.response.ErrorResponse;
import utd.group12.weatherwarning.response.LoginResponse;
import utd.group12.weatherwarning.response.UserResponse;
import utd.group12.weatherwarning.user.UserLogin;
import utd.group12.weatherwarning.user.UserLogin.UsernameTokenPair;

/**
 * Handles the user related requests
 */
@RestController
public class UserController {
	
	private final String getUserEndpoint = "/api/user";
	/**
	 * Handles the request for current logged in user
	 * 
	 * @param username	the currently logged in username
	 * @param token		a given token for the currently logged in user 
	 * @return			an error or the user information
	 */
	@GetMapping(getUserEndpoint)
	public ResponseEntity<?> getUser(@RequestHeader("Auth-Username") String username, @RequestHeader("Auth-Token") String token) {
		if(!UserLogin.isLoggedIn(username, token)) {	// If the user is not logged in return 401 Unauthorized
			return new ErrorResponse(HttpStatus.UNAUTHORIZED, "You are not authenticated.", getUserEndpoint).toResponseEntity();
		}
		
		DataUser user = WeatherWarningApplication.data.getUsers().getUser(username);	// Get the user and return that with 302 Found
																						// We know it exits as it is confirmed they are logged in
		return new ResponseEntity<UserResponse>(new UserResponse(user), HttpStatus.FOUND);
	}
	
	private final String googleLoginEndpoint = "/api/user/google_login";
	/**
	 * Log the google user in or create an account for them
	 * 
	 * @param code	the code from Google's OAuth
	 * @return		an error or the token, username, and token expiration
	 */
	@PostMapping(googleLoginEndpoint)
	public ResponseEntity<?> googleLogin(@RequestParam(value = "code") String code) {
		// Try and get the user's ID and email from google
		UserInfoResponce userInfo;
		try {
			userInfo = GoogleLogin.getUserInfo(code);
		} catch (IOException e) {	// Respond with 500 Internal Server Error if there was an error
			return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error authenticating with google.", googleLoginEndpoint).toResponseEntity();
		}
		
		// Get the username and generate a token 
		UsernameTokenPair usernameTokenPair = UserLogin.googleLogin(userInfo.getId(), userInfo.getEmail());
		if(usernameTokenPair == null) {		// If there was an error and the token was not generated return error
			return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error logging in.", googleLoginEndpoint).toResponseEntity();
		}
		// Return with requested information
		return new ResponseEntity<>(new LoginResponse(usernameTokenPair), HttpStatus.CREATED);
	}
	
	private final String logoutEndpoint = "/api/user/logout";
	/**
	 * Handles the request to logout a user
	 * 
	 * @param username	the currently logged in username
	 * @param token		a given token for the currently logged in user 
	 * @return			an error or the user information
	 */
	@DeleteMapping(logoutEndpoint)
	public ResponseEntity<?> logout(@RequestHeader("Auth-Username") String username, @RequestHeader("Auth-Token") String token) {
		if(!UserLogin.isLoggedIn(username, token)) {	// If the user is not logged in return 401 Unauthorized
			return new ErrorResponse(HttpStatus.UNAUTHORIZED, "You are not authenticated.", logoutEndpoint).toResponseEntity();
		}
		
		UserLogin.logout(username, token); // Logout the user
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	// TODO normal login & register
}