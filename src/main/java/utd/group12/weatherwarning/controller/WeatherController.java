/**
 * Returns all the random info to the client
 */
package utd.group12.weatherwarning.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import utd.group12.weatherwarning.core.Core;
import utd.group12.weatherwarning.data.DataWeather;
import utd.group12.weatherwarning.data.DataWeather.WeatherInfo;
import utd.group12.weatherwarning.errors.InternalServerError;
import utd.group12.weatherwarning.errors.NotImplementedError;
import utd.group12.weatherwarning.errors.UnathorizedError;

/**
 * Handles the weather related requests
 */
@RestController
public class WeatherController extends BaseController{
	
	/**
	 * Gets the current weather for the location
	 * 
	 * @param username				the user's username
	 * @param token					the user's token
	 * @param location				the location to find weather for 
	 * @return						the current weather
	 * @throws UnathorizedError		if the user is not authorized
	 * @throws InternalServerError	if there is an error getting the point info or current weather
	 */
	@GetMapping("/api/user/location/{location}/current")
	public ResponseEntity<DataWeather> getCurrentWeather(
			@RequestHeader("Auth-Username") String username,
			@RequestHeader("Auth-Token") String token,
			@PathVariable String location) throws UnathorizedError, InternalServerError, NotImplementedError {
		Core.instance.users.requireLogin(username, token);		// Require them to be logged in
		
		// Get lat and lng
		double lat;
		double lng;
		if(location.contains(",")) {	// If we were given lat and lng then prase them
			String[] split = location.split(",");
			lat = Double.parseDouble(split[0]);
			lng = Double.parseDouble(split[1]);
		}else {	// Else load from storage
			// TODO get from storage
			throw new NotImplementedError("Use lat,lng");
		}
		
		WeatherInfo weatherInfo = Core.instance.weather.getWeatherInfo(lat, lng);	// Get point info
		
		return new ResponseEntity<DataWeather>(Core.instance.weather.getCurrentWeather(weatherInfo), HttpStatus.OK);	// Get the current weather and return that with 302 Found
	}
	
	/**
	 * Gets weather information on the requested location
	 * 
	 * @param username				the user's username
	 * @param token					the user's token
	 * @param location				the location to find info for 
	 * @return						the weather info
	 * @throws UnathorizedError		if the user is not authorized
	 * @throws InternalServerError	if there is an error getting the point info
	 */
	@GetMapping("/api/user/location/{location}/info")
	public ResponseEntity<WeatherInfo> getLocationInfo(
			@RequestHeader("Auth-Username") String username,
			@RequestHeader("Auth-Token") String token,
			@PathVariable String location) throws UnathorizedError, InternalServerError, NotImplementedError {
		Core.instance.users.requireLogin(username, token);		// Require them to be logged in
		
		// Get lat and lng
		double lat;
		double lng;
		if(location.contains(",")) {
			String[] split = location.split(",");
			lat = Double.parseDouble(split[0]);
			lng = Double.parseDouble(split[1]);
		}else {
			// TODO get from storage
			throw new NotImplementedError("Use lat,lng");
		}
		
		return new ResponseEntity<WeatherInfo>(Core.instance.weather.getWeatherInfo(lat, lng), HttpStatus.OK);	// Get the weather info and return that with 302 Found
	}
	
}