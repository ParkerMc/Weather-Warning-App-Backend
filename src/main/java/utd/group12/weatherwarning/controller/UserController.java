/**
 * Returns all the random info to the client
 */
package utd.group12.weatherwarning.controller;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


@RestController
public class UserController {
	
	private final String getUserEndpoint = "/api/user";
	@GetMapping(getUserEndpoint)
	public ResponseEntity<?> getUser(@RequestHeader("Auth-Username") String username, @RequestHeader("Auth-Token") String token) {
		if(!UserLogin.isLoggedIn(username, token)) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(HttpStatus.UNAUTHORIZED, "You are not authenticated.", getUserEndpoint), HttpStatus.UNAUTHORIZED);
		}
		DataUser user = WeatherWarningApplication.data.getUsers().getUser(username);
		return new ResponseEntity<UserResponse>(new UserResponse(user.getUsername(), user.getEmail(), user.getPhoneNumber()), HttpStatus.FOUND);
	}
	
	private final String googleLoginEndpoint = "/api/user/google_login";
	@PostMapping(googleLoginEndpoint)
	public ResponseEntity<?> googleLogin(@RequestParam(value = "code") String code) {
		UserInfoResponce userInfo;
		try {
			userInfo = GoogleLogin.getUserInfo(code);
		} catch (IOException e) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error authenticating with google.", googleLoginEndpoint), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		UsernameTokenPair usernameTokenPair = UserLogin.GoogleLogin(userInfo.getId(), userInfo.getEmail());
		if(usernameTokenPair == null) {
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error logging in.", googleLoginEndpoint), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new LoginResponse(usernameTokenPair.getUsername(), usernameTokenPair.getToken(), usernameTokenPair.getTokenExp()), HttpStatus.CREATED);
	}
}