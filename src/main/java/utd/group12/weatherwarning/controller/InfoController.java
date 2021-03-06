package utd.group12.weatherwarning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import utd.group12.weatherwarning.core.Core;
import utd.group12.weatherwarning.core.google.Google;
import utd.group12.weatherwarning.response.InfoResponse;


/**
 * Handles the info requests
 */
@RestController
public class InfoController extends BaseController {

	/**
	 * Responds with the random info the client needs
	 * 
	 * @return	the random info
	 */
	@GetMapping("/api/info")
	public InfoResponse info() {
		Google google = Core.instance.google;
		return new InfoResponse(google.login.getLoginUrl(), google.getServerGoogleClientID(), google.getAPIKey());
	}
}