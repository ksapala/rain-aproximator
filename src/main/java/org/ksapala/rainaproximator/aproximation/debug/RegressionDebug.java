package org.ksapala.rainaproximator.aproximation.debug;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.regression.RegressionPoint;

import java.util.List;

@AllArgsConstructor
@Getter
public class RegressionDebug {

    public final static RegressionDebug NAN_REGRESSION_DEBUG = new RegressionDebug(Double.NaN, Double.NaN, Double.NaN,
            "0/0", new Boolean[0]);

    private double standardDeviation;
    private double slope;
    private double rSquare;
    private String pointsCount;

    @Getter(AccessLevel.NONE)
    private Boolean[] whichRegressionPointsWereTaken;

    public static RegressionDebug of(double standardDeviation, double regressionSlope, double rSquare,
                                     List<RegressionPoint> allRegressionPoints, List<RegressionPoint> takenRegressionPoints) {

        Boolean[] whichRegressionPointsWereTaken = RegressionDebug.whichRegressionPointsWereTaken(allRegressionPoints, takenRegressionPoints);

        String pointsCount = takenRegressionPoints.size() + "/" + allRegressionPoints.size();
        return new RegressionDebug(standardDeviation, regressionSlope, rSquare, pointsCount, whichRegressionPointsWereTaken);
    }


    public static Boolean[] whichRegressionPointsWereTaken(List<RegressionPoint> allRegressionPoints,
                                                          List<RegressionPoint> takenRegressionPoints) {
        return allRegressionPoints.stream().map(p -> takenRegressionPoints.contains(p)).toArray(Boolean[]::new);
    }

    public Boolean[] whichRegressionPointsAreTaken() {
        return whichRegressionPointsWereTaken;
    }
}
