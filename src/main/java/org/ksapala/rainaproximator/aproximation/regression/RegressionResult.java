/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.debug.RegressionDebug;
import org.ksapala.rainaproximator.utils.TimeUtils;

/**
 * @author krzysztof
 *
 */
@AllArgsConstructor
@Getter
public class RegressionResult {

    public final static RegressionResult NAN_RESULT = new RegressionResult(Double.NaN, Double.NaN, Double.NaN,
            RegressionDebug.NAN_REGRESSION_DEBUG);

	private double value;
    private double slope;
    private double rSquare;
    private RegressionDebug regressionDebug;


    @Override
    public String toString() {
        return "RegressionResult {" +
                "value=" + TimeUtils.millisToLocalDateAndTime((long) value) +
                ", slope=" + slope +
                ", rSquare=" + rSquare +
                '}';
    }
}
