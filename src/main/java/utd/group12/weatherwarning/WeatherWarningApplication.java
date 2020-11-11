package utd.group12.weatherwarning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import utd.group12.weatherwarning.data.IDataServer;
import utd.group12.weatherwarning.data.json.DataServer;

/**
 * Main file
 */
@SpringBootApplication		// Registers our application for the REST API
public class WeatherWarningApplication {
	public static IDataServer data;		// Holds all data
	
	public static void main(String[] args) {
		data = new DataServer(); 	// Set the data handler (can be changed later)
		data.start();
		SpringApplication.run(WeatherWarningApplication.class, args); // Starts in other thread
		data.stop();
	}

}
