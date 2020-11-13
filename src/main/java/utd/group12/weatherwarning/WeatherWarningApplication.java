package utd.group12.weatherwarning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import utd.group12.weatherwarning.data.IDataServer;
import utd.group12.weatherwarning.data.json.DataServer;

/**
 * Main file
 */
@SpringBootApplication		// Registers our application for the REST API
public class WeatherWarningApplication {
	public static IDataServer data;		// Holds all data
	private static ConfigurableApplicationContext spring;
	
	public static void main(String[] args) {
		data = new DataServer(); 	// Set the data handler (can be changed later)
		data.start();
		spring = SpringApplication.run(WeatherWarningApplication.class, args); // Starts in other thread

		// Register shutdown hook
		Runtime.getRuntime().addShutdownHook(
				new Thread("app-shutdown-hook") {
				    @Override 
				    public void run() {
				      onShutdown();
				    }
				});
	}
	
	/**
	 * Runs when the program is being terminated
	 */
	public static void onShutdown() {
		spring.close();
		data.stop();
	}

}
