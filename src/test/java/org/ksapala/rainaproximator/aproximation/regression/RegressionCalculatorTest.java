/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;
import org.springframework.boot.test.context.SpringBootTest;
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

//	/**
//	 * Test method for {@link RegressionCalculator#removeOutliers(java.util.List)}.
//	 */
//	@Test
//	public void testRemoveOutliersOneRemoved() {
//		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//		points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(12), LocalDateTime.now()));
//		
//		List<RegressionPoint> removed = RegressionCalculator.removeOutliers(points);
//		
//		assertEquals(points.size() - 1, removed.size());
//	}
	
//	/**
//	 * Test method for {@link RegressionCalculator#removeOutliers(java.util.List)}.
//	 */
//	@Test
//	public void testRemoveOutliersNoRemove() {
//		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//		points.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(4), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(8), LocalDateTime.now()));
//		
//		List<RegressionPoint> removed = RegressionCalculator.removeOutliers(points);
//		
//		assertEquals(points.size(), removed.size());
//	}
	
//	@Test
//	public void testRemoveOutliersZeroDistance() {
//		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//		points.add(new RegressionPoint(new Distance(0), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(0), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(0), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(7), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(8), LocalDateTime.now()));
//		
//		List<RegressionPoint> removed = RegressionCalculator.removeOutliers(points);
//		
//		assertEquals(points.size() - 2, removed.size());
//	}

//	@Test
//	public void testRemoveOutliersSomeRemoved() {
//		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//		points.add(new RegressionPoint(new Distance(100), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(100), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(100), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(100), LocalDateTime.now()));
//		points.add(new RegressionPoint(new Distance(165), LocalDateTime.now()));
//		
//		List<RegressionPoint> removed = RegressionCalculator.removeOutliers(points);
//		
//		System.out.println(removed.size());
//	}
}
