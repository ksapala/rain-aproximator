/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

import org.ksapala.rainaproximator.utils.TimeUtils;

/**
 * @author krzysztof
 *
 */
public class RegressionResult {

    public final static RegressionResult NAN_RESULT = new RegressionResult(Double.NaN, Double.NaN);

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


    @Override
    public String toString() {
        return "RegressionResult {" +
                "value=" + TimeUtils.millisToLocalDateAndTime((long) value) +
                ", slope=" + slope +
                '}';
    }
}
