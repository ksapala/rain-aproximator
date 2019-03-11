package org.ksapala.rainaproximator.aproximation.debug;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.regression.RegressionPoint;

import java.util.List;

@AllArgsConstructor
@Getter
public class RegressionDebug {

    public final static RegressionDebug NAN_REGRESSION_DEBUG = new RegressionDebug(Double.NaN, Double.NaN, Double.NaN,
            Double.NaN, 0);

    private double standardDeviation;
    private double slope;
    private double velocity;
    private double rSquare;
    private int pointsCount;


    public static RegressionDebug of(double standardDeviation, double regressionSlope, double rSquare,
                                     List<RegressionPoint> points) {
        return new RegressionDebug(standardDeviation, regressionSlope, regressionSlope / 1000 / 60, rSquare, points.size());
    }

}
