/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.wind;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.ksapala.rainaproximator.BaseTest;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.aproximation.wind.WindGetter;

/**
 * @author krzysztof
 *
 * contact: krzysztof.sapala@gmail.com
 *
 */
public class WindGetterTest extends BaseTest {

	@Test
	public void testGetWindDirection() throws IOException, AproximationException {
		WindGetter windGetter = new WindGetter();
		
		double windDirection = windGetter.getWindDirection(20, 50);
		
		assertTrue(windDirection >= 0);
		assertTrue(windDirection <= 360);
		System.out.println(windDirection);
	}

}
