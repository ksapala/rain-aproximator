/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

/**
 * @author krzysztof
 *
 */
public class RegressionResult {

	private double value;
	private double slope;
	

	public RegressionResult(double value, double slope) {
		this.value = value;
		this.slope = slope;
	}

	public double getValue() {
		return this.value;
	}

	public double getSlope() {
		return this.slope;
	}

}
