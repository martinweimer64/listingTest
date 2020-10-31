package com.weimer.listingTest;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ListingTestApplication {

	public static void main(String[] args) {
		final SpringApplicationBuilder builder = new SpringApplicationBuilder(ListingTestApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}

	@PostConstruct
	public void startDBManager() {
		//DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", "" });
	}
}
