/*
 * GreetingController registers the endpoint "/greeting" and returns the a Greeting object that will be converted to json automatically
 * To test it you can run the program and in your web browser go to "http://localhost:8080/greeting"
 * 
 * you can also add parameters to the url, see examples below
 * http://localhost:8080/greeting?name=Test
 * http://localhost:8080/greeting?service_name=Test_Service
 * http://localhost:8080/greeting?name=Test&service_name=Test_Service
 */
package utd.group12.weatherwarning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utd.group12.weatherwarning.response.Greeting;


@RestController		// Registers our controller so the function will get called
public class GreetingController {

	private static final String template = "Hello, %s!";

	@GetMapping("/greeting")		// Register this method with the endpoint (can have multiple function per class)
	public Greeting greeting(@RequestParam(value = "service_name", defaultValue = "Weather Warning") String service_name,	// add param @RequestParam registers it so it will get the pramiter from the http request
			@RequestParam(value = "name", defaultValue = "World") String name) {			 // Second param
		// More code can run here
		return new Greeting(service_name, String.format(template, name));	// But when we return an object it will convert it to json
	}
}