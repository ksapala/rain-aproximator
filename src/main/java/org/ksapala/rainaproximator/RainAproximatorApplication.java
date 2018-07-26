package org.ksapala.rainaproximator;

import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(Configuration.class)
public class RainAproximatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(RainAproximatorApplication.class, args);
	}
}
