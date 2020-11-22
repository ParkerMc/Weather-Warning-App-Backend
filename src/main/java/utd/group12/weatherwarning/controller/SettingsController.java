/**
 * Returns all the random info to the client
 */
package utd.group12.weatherwarning.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utd.group12.weatherwarning.core.Core;
import utd.group12.weatherwarning.errors.BadRequestError;
import utd.group12.weatherwarning.errors.NotFoundError;
import utd.group12.weatherwarning.errors.UnathorizedError;
import utd.group12.weatherwarning.response.SettingsResponse;

/**
 * Handles the settings related requests
 */
@RestController
public class SettingsController extends BaseController{
	
	/**
	 * Handles the request for current logged in user's settings
	 * 
	 * @param username			the currently logged in username
	 * @param token				a given token for the currently logged in user 
	 * @return					an error or the user's settings
	 * @throws UnathorizedError	if the user is not authenticated
	 * @throws NotFoundError 		if the user's settings were not found
	 */
	@GetMapping("/api/user/settings")
	public ResponseEntity<SettingsResponse> get(
			@RequestHeader("Auth-Username") String username,
			@RequestHeader("Auth-Token") String token) throws UnathorizedError, NotFoundError {
		Core.instance.users.requireLogin(username, token);		// Require them to be logged in
		
		return new ResponseEntity<SettingsResponse>(new SettingsResponse(Core.instance.settings.get(username)), HttpStatus.OK);	// Get the settings and return that with 302 Found
	}
	
	/**
	 * Updates a the user's settings
	 * 
	 * @param username				the user's username
	 * @param darkMode				the user's new dark mode setting
	 * @param useGPS				the user's new GPS setting
	 * @param useMetric				the user's unit setting
	 * @param mainLocation			the user's new main location
	 * @return						the updated settings object
	 * @throws UnathorizedError		if the user is not logged in
	 * @throws BadRequestError 		if the data is invalid
	 * @throws NotFoundError 		if the user's settings were not found
	 */
	@PutMapping("/api/user/settings")
	public ResponseEntity<SettingsResponse> update(
			@RequestHeader("Auth-Username") String username,
			@RequestHeader("Auth-Token") String token,
			@RequestParam(value = "darkMode", required=false) Boolean darkMode,
			@RequestParam(value = "useGPS", required=false) Boolean useGPS,
			@RequestParam(value = "useMetric", required=false) Boolean useMetric,
			@RequestParam(value = "mainLocation", required=false) Integer mainLocation) throws UnathorizedError, BadRequestError, NotFoundError {
		Core.instance.users.requireLogin(username, token);		// Require them to be logged in
		
		Core.instance.settings.update(username, darkMode, useGPS, useMetric, mainLocation);
		
		try {
			return new ResponseEntity<SettingsResponse>(new SettingsResponse(Core.instance.settings.get(username)), HttpStatus.OK);	// Get the settings and return that with 302 Found
		} catch (NotFoundError e) {
			throw new RuntimeException();		// Should never happen as we just updated them
		}	
	}
}