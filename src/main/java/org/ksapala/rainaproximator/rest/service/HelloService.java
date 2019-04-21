/**
 * 
 */
package org.ksapala.rainaproximator.rest.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


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
    public String helloString() {
//        String hello = messageSource.getMessage("Test.hello", new Object[0], Locale.getDefault());
        return "hello";
    }


    public HelloResponse hello() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        JSONObject object = new JSONObject(map);

        return new HelloResponse("sample first", "sample second", new HelloComplex("111", "222"));
    }

	@Getter
    @AllArgsConstructor
    public class HelloResponse {
        private String first;
        private String second;
        private HelloComplex HelloComplex;
    }

    @Getter
    @AllArgsConstructor
    public class HelloComplex {
        private String item1;
        private String item2;
    }

}