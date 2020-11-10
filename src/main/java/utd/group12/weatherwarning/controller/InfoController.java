/**
 * Returns all the random info to the client
 */
package utd.group12.weatherwarning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import utd.group12.weatherwarning.google.GoogleLogin;
import utd.group12.weatherwarning.response.InfoResponse;


@RestController
public class InfoController {

	@GetMapping("/api/info")
	public InfoResponse info() {
		return new InfoResponse(GoogleLogin.getLoginUrl());
	}
}