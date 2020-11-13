package utd.group12.weatherwarning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utd.group12.weatherwarning.response.GreetingResponse;

/**
 * GreetingController registers the endpoint "/api/greeting" and returns the a Greeting object that will be converted to JSON automatically		<br/>
 * To test it you can run the program and in your web browser go to "http://localhost:8080/api/greeting"	<br/><br/>
 * 
 * you can also add parameters to the URL, see examples below:	<br/>
 * http://localhost:8080/api/greeting?name=Test		<br/>
 * http://localhost:8080/api/greeting?service_name=Test_Service		<br/>
 * http://localhost:8080/api/greeting?name=Test&service_name=Test_Service	<br/>
 */
@RestController		// Registers our controller so the function will get called
public class GreetingController extends BaseController {

	private static final String template = "Hello, %s!";

	/**
	 * 
	 * @param service_name	the service name to include in the response. Defaults to "Weather Warning"
	 * @param name			the name to say hello to. Defaults to "World" 
	 * @return				the generated response to send to the client
	 */
	@GetMapping("/api/greeting")		// Register this method with the endpoint (can have multiple function per class)
	public GreetingResponse greeting(@RequestParam(value = "service_name", defaultValue = "Weather Warning") String service_name,	// add parameter @RequestParam registers it so it will get the parameter from the HTTP request
			@RequestParam(value = "name", defaultValue = "World") String name) {			 // Second parameter
		// More code can run here
		return new GreetingResponse(service_name, String.format(template, name));	// But when we return an object it will convert it to JSON
	}
}