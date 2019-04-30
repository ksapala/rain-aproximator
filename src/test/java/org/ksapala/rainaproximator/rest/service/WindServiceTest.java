/**
 * 
 */
package org.ksapala.rainaproximator.rest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.scan.Scanner;
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
public class WindServiceTest {

    @Autowired
    private WindService windService;

    @MockBean
    private Scanner scanner;

    @Test
	public void testGetWindDirection() {
        Optional<Integer> windDirection = windService.getWindDirection(20, 50);

        if (windDirection.isPresent()) {
            assertTrue(windDirection.get() >= 0);
            assertTrue(windDirection.get() <= 360);
        }
	}

}
