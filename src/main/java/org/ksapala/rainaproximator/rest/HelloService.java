/**
 * 
 */
package org.ksapala.rainaproximator.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.aproximation.AproximationResult;
import org.ksapala.rainaproximator.aproximation.RainAproximator;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.ScanComponent;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;
import org.ksapala.rainaproximator.rest.factory.AproximationResultBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;


/**
 * @author krzysztof
 *
 */
@Component
public class HelloService {

	private final static Logger LOGGER = LogManager.getLogger(HelloService.class);

    @Autowired
    private MessageSource messageSource;

    public HelloService() {
	}

	/**
	 */
	public String hello() {
//        String hello = messageSource.getMessage("Test.hello", new Object[0], Locale.getDefault());
        return "hello";
	}


}