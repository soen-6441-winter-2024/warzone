package ca.concordia.app.warzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * The main class that starts the Warzone application.
 * This class is annotated with "@SpringBootApplication" to enable Spring Boot features.
 */
@SpringBootApplication
public class WarzoneApplication {

	/**
	 * Default constructor
	 */
	public WarzoneApplication() {}
	/**
	 * The main method of the application. It starts the Spring application context and launches the application.
	 *
	 * @param args The command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(WarzoneApplication.class, args);
	}

}

