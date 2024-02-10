package ca.concordia.app.warzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
public class WarzoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarzoneApplication.class, args);
	}

}
