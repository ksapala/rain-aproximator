/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.wind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private Configuration configuration;

    @Test
	public void testGetWindDirection() throws AproximationException {
		WindGetter windGetter = new WindGetter(configuration.getWind());
		
		double windDirection = windGetter.getWindDirection(20, 50);
		
		assertTrue(windDirection >= 0);
		assertTrue(windDirection <= 360);
		System.out.println(windDirection);
	}

}
