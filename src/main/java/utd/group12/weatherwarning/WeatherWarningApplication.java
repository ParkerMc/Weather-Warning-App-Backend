package utd.group12.weatherwarning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication		// Registers our application for the REST api
public class WeatherWarningApplication {

	public static void main(String[] args) {
		// Pre server start code can go here
		SpringApplication.run(WeatherWarningApplication.class, args); // Starts in other thread
		// More code can go here to run in main thread after the server starts
	}

}
