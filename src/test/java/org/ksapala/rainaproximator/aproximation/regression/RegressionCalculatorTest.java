package org.ksapala.rainaproximator.aproximation.regression;

import org.junit.Test;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author krzysztof
 */
public class RegressionCalculatorTest {

    @Test
    public void shouldGetDifferencesStandardDeviation() {
        // given
        List<RegressionPoint> regressionPoints = new ArrayList<>();
        regressionPoints.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
        regressionPoints.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
        regressionPoints.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
        regressionPoints.add(new RegressionPoint(new Distance(8), LocalDateTime.now()));
        regressionPoints.add(new RegressionPoint(new Distance(10), LocalDateTime.now()));
        RegressionCalculator regressionCalculator = new RegressionCalculator(regressionPoints);

        // when
        double differencesStandardDeviation = regressionCalculator.getDifferencesStandardDeviation(regressionPoints);

        // then
        assertEquals(0.95742, differencesStandardDeviation, 0.0001);
    }

    @Test
    public void shouldGetDifferencesStandardDeviationMinusDifferences() {
        // given
        List<RegressionPoint> regressionPoints = new ArrayList<>();
        regressionPoints.add(new RegressionPoint(new Distance(5), LocalDateTime.now()));
        regressionPoints.add(new RegressionPoint(new Distance(3), LocalDateTime.now()));
        regressionPoints.add(new RegressionPoint(new Distance(6), LocalDateTime.now()));
        regressionPoints.add(new RegressionPoint(new Distance(8), LocalDateTime.now()));
        regressionPoints.add(new RegressionPoint(new Distance(10), LocalDateTime.now()));
        RegressionCalculator regressionCalculator = new RegressionCalculator(regressionPoints);

        // when
        double differencesStandardDeviation = regressionCalculator.getDifferencesStandardDeviation(regressionPoints);

        // then
        assertEquals(2.21735, differencesStandardDeviation, 0.0001);
    }
}