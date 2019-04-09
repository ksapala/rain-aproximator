package org.ksapala.rainaproximator;

import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(Configuration.class)
public class RainAproximatorApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RainAproximatorApplication.class, args);
    }

}
