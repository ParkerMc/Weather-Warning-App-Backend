/**
 * Returns all the random info to the client
 */
package utd.group12.weatherwarning.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utd.group12.weatherwarning.core.Core;
import utd.group12.weatherwarning.core.Users.UsernameTokenPair;
import utd.group12.weatherwarning.core.google.GoogleLogin.UserInfoResponce;
import utd.group12.weatherwarning.errors.BadRequestError;
import utd.group12.weatherwarning.errors.ConflictError;
import utd.group12.weatherwarning.errors.InternalServerError;
import utd.group12.weatherwarning.errors.NotFoundError;
import utd.group12.weatherwarning.errors.UnathorizedError;
import utd.group12.weatherwarning.response.LoggedinResponse;
import utd.group12.weatherwarning.response.LoginResponse;
import utd.group12.weatherwarning.response.UserResponse;

/**
 * Handles the user related requests
 */
@RestController
public class UserController extends BaseController{
	// TODO add forgot password
	// TODO add email verification
	// TODO add add delete account
	
	/**
	 * Handles the request for current logged in user
	 * 
	 * @param username			the currently logged in username
	 * @param token				a given token for the currently logged in user 
	 * @return					an error or the user information
	 * @throws UnathorizedError	if the user is not authenticated
	 */
	@GetMapping("/api/user")
	public ResponseEntity<UserResponse> getUser(
			@RequestHeader("Auth-Username") String username,
			@RequestHeader("Auth-Token") String token) throws UnathorizedError {
		Core.instance.users.requireLogin(username, token);		// Require them to be logged in
		
		try {
			return new ResponseEntity<UserResponse>(new UserResponse( Core.instance.users.get(username)), HttpStatus.OK);	// Get the user and return that with 302 Found
		} catch (NotFoundError e) {
			throw new RuntimeException();		// We know the user exits as they are logged in
		}	
	}
	
	/**
	 * Log the google user in or create an account for them
	 * 
	 * @param code					the code from Google's OAuth
	 * @return						an error or the token, username, and token expiration
	 * @throws InternalServerError 	if there is a google API error
	 * @throws ConflictError 		if the email has already been used under a different account (likely non google)
	 */
	@PostMapping("/api/user/google_login")
	public ResponseEntity<LoginResponse> googleLogin(@RequestParam(value = "code") String code) throws InternalServerError, ConflictError {
		// Try and get the user's ID and email from google
		UserInfoResponce userInfo = Core.instance.google.login.getUserInfo(code);
		
		// Get the username and generate a token 
		UsernameTokenPair usernameTokenPair = Core.instance.users.googleLogin(userInfo.getId(), userInfo.getEmail());

		// Return with requested information
		return new ResponseEntity<LoginResponse>(new LoginResponse(usernameTokenPair), HttpStatus.CREATED);
	}
	
	/**
	 * Checks to see if the user is logged in
	 * 
	 * @param username			the currently logged in username
	 * @param token				a given token for the currently logged in user 
	 * @return					if the user is logged in
	 */
	@GetMapping("/api/user/loggedin")
	public ResponseEntity<LoggedinResponse> isLoggedin(
			@RequestHeader("Auth-Username") String username,
			@RequestHeader("Auth-Token") String token)  {		
		return new ResponseEntity<LoggedinResponse>(new LoggedinResponse(Core.instance.users.isLoggedIn(username, token)), HttpStatus.OK);
	}
	
	/**
	 * Handles the request to login a user
	 * 
	 * @param identifier		user's username or email 
	 * @param password			new user's password
	 * @return					an error or the token, username, and token expiration
	 * @throws UnathorizedError	When the identifier can't be used or the password is wrong
	 */
	@PostMapping("/api/user/login")
	public ResponseEntity<LoginResponse> login(
			@RequestParam(value = "identifier") String identifier,
			@RequestParam(value = "password") String password) throws UnathorizedError {
		
		// Get the actual username and generate a token 
		UsernameTokenPair usernameTokenPair = Core.instance.users.login(identifier, password);
		
		// Return with requested information
		return new ResponseEntity<LoginResponse>(new LoginResponse(usernameTokenPair), HttpStatus.CREATED);
	}
	
	/**
	 * Handles the request to logout a user
	 * 
	 * @param username	the currently logged in username
	 * @param token		a given token for the currently logged in user 
	 * @return			an error or http no content code
	 * @throws UnathorizedError	if the user is not authenticated
	 */
	@DeleteMapping("/api/user/logout")
	public ResponseEntity<?> logout(
			@RequestHeader("Auth-Username") String username,
			@RequestHeader("Auth-Token") String token) throws UnathorizedError {
		Core.instance.users.requireLogin(username, token);		// Require them to be logged in
		
		try {
			Core.instance.users.logout(username, token); 	// Logout the user
		} catch (NotFoundError e) {
			throw new RuntimeException(); // Should not ever happen as we know the user exists
		} 
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Handles the request to logout a user
	 * 
	 * @param username	new user's username
	 * @param email		new user's email 
	 * @param password	new user's password
	 * @return			an error or the token, username, and token expiration
	 * @throws BadRequestError 	if the username or email is invalid
	 * @throws ConflictError	if the user or email is already used
	 */
	@PostMapping("/api/user")
	public ResponseEntity<LoginResponse> register(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "name", defaultValue="") String name,
			@RequestParam(value = "phoneNumber", defaultValue="") String phoneNumber) throws ConflictError, BadRequestError {
		
		UsernameTokenPair usernameTokenPair = Core.instance.users.create(username, email, password, name, phoneNumber);
		
		return new ResponseEntity<LoginResponse>(new LoginResponse(usernameTokenPair), HttpStatus.CREATED);
	}
	
	
	/**
	 * 
	 * @param username				the user's username
	 * @param token					the user's token
	 * @param email					the user's new email
	 * @param password				the user's new password
	 * @param name					the user's new name
	 * @param phoneNumber			the user's new phone number
	 * @return						the updated user object
	 * @throws UnathorizedError		if the user is not logged in
	 * @throws ConflictError		if the email is already used by someone else
	 * @throws BadRequestError 		if the email or password is invalid
	 */
	@PutMapping("/api/user")
	public ResponseEntity<UserResponse> updateData(
			@RequestHeader("Auth-Username") String username,
			@RequestHeader("Auth-Token") String token,
			@RequestParam(value = "email", required=false) String email,
			@RequestParam(value = "password", required=false) String password,
			@RequestParam(value = "name", required=false) String name,
			@RequestParam(value = "phoneNumber", required=false) String phoneNumber) throws UnathorizedError, BadRequestError, ConflictError {
		Core.instance.users.requireLogin(username, token);		// Require them to be logged in
		
		try {
			Core.instance.users.update(username, email, password, name, phoneNumber);
		} catch (NotFoundError e) {
			throw new RuntimeException();	// We know this should never happen because they're logged in
		}
		
		try {
			return new ResponseEntity<UserResponse>(new UserResponse( Core.instance.users.get(username)), HttpStatus.OK);	// Get the user and return that with 302 Found
		} catch (NotFoundError e) {
			throw new RuntimeException();		// We know the user exits as they are logged in
		}	
	}
}