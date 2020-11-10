package utd.group12.weatherwarning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import utd.group12.weatherwarning.data.IDataServer;
import utd.group12.weatherwarning.data.json.DataServer;

@SpringBootApplication		// Registers our application for the REST api
public class WeatherWarningApplication {
	public static IDataServer data;
	
	public static void main(String[] args) {
		data = new DataServer();
		data.start();
		SpringApplication.run(WeatherWarningApplication.class, args); // Starts in other thread
		data.stop();
	}

}
