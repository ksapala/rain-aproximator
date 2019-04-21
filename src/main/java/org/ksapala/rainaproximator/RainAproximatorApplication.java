package org.ksapala.rainaproximator;

import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@EnableScheduling
@EnableConfigurationProperties(Configuration.class)
//@Import({ScanComponent.class })
//@ComponentScan(basePackages = {"org.ksapala.rainaproximator.rest",
//        "org.ksapala.rainaproximator.configuration",
//        "org.ksapala.rainaproximator.aproximation"}, lazyInit = true)
public class RainAproximatorApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RainAproximatorApplication.class, args);
    }


    /*
     * Create required HandlerMapping, to avoid several default HandlerMapping instances being created
//     */
//    @Bean
//    public HandlerMapping handlerMapping() {
//        return new RequestMappingHandlerMapping();
//    }
//
//    /*
//     * Create required HandlerAdapter, to avoid several default HandlerAdapter instances being created
//     */
//    @Bean
//    public HandlerAdapter handlerAdapter() {
//        return new RequestMappingHandlerAdapter();
//    }


}
