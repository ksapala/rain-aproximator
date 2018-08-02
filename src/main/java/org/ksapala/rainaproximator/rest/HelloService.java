/**
 * 
 */
package org.ksapala.rainaproximator.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


/**
 * @author krzysztof
 *
 */
@Component
public class HelloService {


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