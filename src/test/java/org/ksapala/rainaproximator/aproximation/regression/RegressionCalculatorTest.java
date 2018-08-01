/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;
import org.ksapala.rainaproximator.aproximation.scan.Scanner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author krzysztof
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RegressionCalculatorTest {

	private RegressionCalculator calculator;

    @MockBean
    private Scanner scanner;

	public RegressionCalculatorTest() {
		this.calculator = new RegressionCalculator(null);
	}
	
	@Test
	public void testRemoveOutliersDifferenceMinusMedianThreeRemoved() {
		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
		points.add(new RegressionPoint(new Distance(7), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(12), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(11), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(10), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(1), LocalDateTime.now()));
		
		List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
		
		assertEquals(points.size() - 3, removed.size());
	}
	
	@Test
	public void testRemoveOutliersDifferenceMinusMedianNoRemove() {
		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
		points.add(new RegressionPoint(new Distance(7), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
		
		List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
		
		assertEquals(points.size(), removed.size());
	}
	
	@Test
	public void testRemoveOutliersDifferenceThreeRemoved() {
		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
		points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(2), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(9), LocalDateTime.now()));
		
		List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
		
		assertEquals(points.size() - 3, removed.size());
	}
	
	@Test
	public void testRemoveOutliersDifferenceNoRemove() {
		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
		points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
		points.add(new RegressionPoint(new Distance(7), LocalDateTime.now()));
		
		List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
		
		assertEquals(points.size(), removed.size());
	}

}
