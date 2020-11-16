package utd.group12.weatherwarning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import utd.group12.weatherwarning.core.Core;

/**
 * Main file
 */
@SpringBootApplication		// Registers our application for the REST API
public class WeatherWarningApplication {
	private static ConfigurableApplicationContext spring;
	
	public static void main(String[] args) {
		Core.instance.start();	// Set the core handler
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
		Core.instance.stop();
	}

}
