/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.wind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.scan.Scanner;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * @author krzysztof
 *
 * contact: krzysztof.sapala@gmail.com
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WindGetterTest {

    @Autowired
    private WindGetter windGetter;

    @MockBean
    private Scanner scanner;

    @Test
	public void testGetWindDirection() throws AproximationException {
        Optional<Double> windDirection = windGetter.getWindDirection(20, 50);

        if (windDirection.isPresent()) {
            assertTrue(windDirection.get() >= 0);
            assertTrue(windDirection.get() <= 360);
        }
	}

}
