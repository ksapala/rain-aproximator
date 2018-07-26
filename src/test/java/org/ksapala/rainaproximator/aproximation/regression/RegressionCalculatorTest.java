/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.ksapala.rainaproximator.BaseTest;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;

/**
 * @author krzysztof
 *
 */
public class RegressionCalculatorTest extends BaseTest {

	private RegressionCalculator calculator;
	
	public RegressionCalculatorTest() {
		this.calculator = new RegressionCalculator(null);
	}
	
	@Test
	public void testRemoveOutliersDifferenceMinusMedianThreeRemoved() {
		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
		points.add(new RegressionPoint(new Distance(7), new Date()));
		points.add(new RegressionPoint(new Distance(6), new Date()));
		points.add(new RegressionPoint(new Distance(5), new Date()));
		points.add(new RegressionPoint(new Distance(12), new Date()));
		points.add(new RegressionPoint(new Distance(11), new Date()));
		points.add(new RegressionPoint(new Distance(10), new Date()));
		points.add(new RegressionPoint(new Distance(1), new Date()));
		
		List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
		
		assertEquals(points.size() - 3, removed.size());
	}
	
	@Test
	public void testRemoveOutliersDifferenceMinusMedianNoRemove() {
		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
		points.add(new RegressionPoint(new Distance(7), new Date()));
		points.add(new RegressionPoint(new Distance(6), new Date()));
		points.add(new RegressionPoint(new Distance(5), new Date()));
		points.add(new RegressionPoint(new Distance(6), new Date()));
		points.add(new RegressionPoint(new Distance(3), new Date()));
		
		List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
		
		assertEquals(points.size(), removed.size());
	}
	
	@Test
	public void testRemoveOutliersDifferenceThreeRemoved() {
		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
		points.add(new RegressionPoint(new Distance(3), new Date()));
		points.add(new RegressionPoint(new Distance(4), new Date()));
		points.add(new RegressionPoint(new Distance(5), new Date()));
		points.add(new RegressionPoint(new Distance(2), new Date()));
		points.add(new RegressionPoint(new Distance(3), new Date()));
		points.add(new RegressionPoint(new Distance(4), new Date()));
		points.add(new RegressionPoint(new Distance(9), new Date()));
		
		List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
		
		assertEquals(points.size() - 3, removed.size());
	}
	
	@Test
	public void testRemoveOutliersDifferenceNoRemove() {
		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
		points.add(new RegressionPoint(new Distance(3), new Date()));
		points.add(new RegressionPoint(new Distance(4), new Date()));
		points.add(new RegressionPoint(new Distance(5), new Date()));
		points.add(new RegressionPoint(new Distance(4), new Date()));
		points.add(new RegressionPoint(new Distance(7), new Date()));
		
		List<RegressionPoint> removed = this.calculator.removeOutliersDifference(points);
		
		assertEquals(points.size(), removed.size());
	}
	
	/**
	 * Test method for {@link RegressionCalculator#removeOutliers(java.util.List)}.
	 */
//	@Test
//	public void testRemoveOutliersOneRemoved() {
//		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//		points.add(new RegressionPoint(new Distance(3), new Date()));
//		points.add(new RegressionPoint(new Distance(4), new Date()));
//		points.add(new RegressionPoint(new Distance(5), new Date()));
//		points.add(new RegressionPoint(new Distance(6), new Date()));
//		points.add(new RegressionPoint(new Distance(12), new Date()));
//		
//		List<RegressionPoint> removed = RegressionCalculator.removeOutliers(points);
//		
//		assertEquals(points.size() - 1, removed.size());
//	}
	
	/**
	 * Test method for {@link RegressionCalculator#removeOutliers(java.util.List)}.
	 */
//	@Test
//	public void testRemoveOutliersNoRemove() {
//		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//		points.add(new RegressionPoint(new Distance(3), new Date()));
//		points.add(new RegressionPoint(new Distance(4), new Date()));
//		points.add(new RegressionPoint(new Distance(5), new Date()));
//		points.add(new RegressionPoint(new Distance(6), new Date()));
//		points.add(new RegressionPoint(new Distance(8), new Date()));
//		
//		List<RegressionPoint> removed = RegressionCalculator.removeOutliers(points);
//		
//		assertEquals(points.size(), removed.size());
//	}
	
//	@Test
//	public void testRemoveOutliersZeroDistance() {
//		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//		points.add(new RegressionPoint(new Distance(0), new Date()));
//		points.add(new RegressionPoint(new Distance(0), new Date()));
//		points.add(new RegressionPoint(new Distance(0), new Date()));
//		points.add(new RegressionPoint(new Distance(7), new Date()));
//		points.add(new RegressionPoint(new Distance(8), new Date()));
//		
//		List<RegressionPoint> removed = RegressionCalculator.removeOutliers(points);
//		
//		assertEquals(points.size() - 2, removed.size());
//	}

//	@Test
//	public void testRemoveOutliersSomeRemoved() {
//		List<RegressionPoint> points = new ArrayList<RegressionPoint>();
//		points.add(new RegressionPoint(new Distance(100), new Date()));
//		points.add(new RegressionPoint(new Distance(100), new Date()));
//		points.add(new RegressionPoint(new Distance(100), new Date()));
//		points.add(new RegressionPoint(new Distance(100), new Date()));
//		points.add(new RegressionPoint(new Distance(165), new Date()));
//		
//		List<RegressionPoint> removed = RegressionCalculator.removeOutliers(points);
//		
//		System.out.println(removed.size());
//	}
}
