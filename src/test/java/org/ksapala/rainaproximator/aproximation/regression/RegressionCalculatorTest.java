package org.ksapala.rainaproximator.aproximation.regression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author krzysztof
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RegressionCalculatorTest {

    @Autowired
    private Configuration configuration;


    @Before
    public void setUp() {
    }

    @Test
    public void shouldGetDifferencesStandardDeviation() {
        // given
        List<RegressionPoint> regressionPoints = new ArrayList<>();
        regressionPoints.add(new RegressionPoint(new Distance(5), TestUtils.parseInTest("04/02/2115 12:00")));
        regressionPoints.add(new RegressionPoint(new Distance(5), TestUtils.parseInTest("04/02/2115 12:10")));
        regressionPoints.add(new RegressionPoint(new Distance(6), TestUtils.parseInTest("04/02/2115 12:20")));
        regressionPoints.add(new RegressionPoint(new Distance(8), TestUtils.parseInTest("04/02/2115 12:30")));
        regressionPoints.add(new RegressionPoint(new Distance(10), TestUtils.parseInTest("04/02/2115 12:40")));
        RegressionCalculator regressionCalculator = new RegressionCalculator(regressionPoints, configuration.getAlgorithm());

        // when
        double differencesStandardDeviation = regressionCalculator.getDifferencesStandardDeviation(regressionPoints);

        // then
        assertEquals(0.9574, differencesStandardDeviation, 0.001);
    }

    @Test
    public void shouldGetDifferencesStandardDeviationMinusDifferences() {
        // given
        List<RegressionPoint> regressionPoints = new ArrayList<>();
        regressionPoints.add(new RegressionPoint(new Distance(5), TestUtils.parseInTest("04/02/2115 12:00")));
        regressionPoints.add(new RegressionPoint(new Distance(3), TestUtils.parseInTest("04/02/2115 12:10")));
        regressionPoints.add(new RegressionPoint(new Distance(6), TestUtils.parseInTest("04/02/2115 12:20")));
        regressionPoints.add(new RegressionPoint(new Distance(8), TestUtils.parseInTest("04/02/2115 12:30")));
        regressionPoints.add(new RegressionPoint(new Distance(10), TestUtils.parseInTest("04/02/2115 12:40")));
        RegressionCalculator regressionCalculator = new RegressionCalculator(regressionPoints, configuration.getAlgorithm());

        // when
        double differencesStandardDeviation = regressionCalculator.getDifferencesStandardDeviation(regressionPoints);

        // then
        assertEquals(2.2173, differencesStandardDeviation, 0.001);
    }

    @Test
    public void shouldGetDifferencesStandardDeviationDoubleTimeInterval() {
        // given
        List<RegressionPoint> regressionPoints = new ArrayList<>();
        regressionPoints.add(new RegressionPoint(new Distance(2), TestUtils.parseInTest("04/02/2115 12:00")));
        regressionPoints.add(new RegressionPoint(new Distance(4), TestUtils.parseInTest("04/02/2115 12:10")));
        regressionPoints.add(new RegressionPoint(new Distance(6), TestUtils.parseInTest("04/02/2115 12:20")));
        regressionPoints.add(new RegressionPoint(new Distance(10), TestUtils.parseInTest("04/02/2115 12:40")));
        RegressionCalculator regressionCalculator = new RegressionCalculator(regressionPoints, configuration.getAlgorithm());

        // when
        double differencesStandardDeviation = regressionCalculator.getDifferencesStandardDeviation(regressionPoints);

        // then
        assertEquals(0.0, differencesStandardDeviation, 0.001);
    }

    @Test
    public void shouldGetDifferencesStandardDeviationDoubleTimeIntervalComplex() {
        // given
        List<RegressionPoint> regressionPoints = new ArrayList<>();
        regressionPoints.add(new RegressionPoint(new Distance(2), TestUtils.parseInTest("04/02/2115 12:00")));
        regressionPoints.add(new RegressionPoint(new Distance(4), TestUtils.parseInTest("04/02/2115 12:10")));
        regressionPoints.add(new RegressionPoint(new Distance(6), TestUtils.parseInTest("04/02/2115 12:20")));
        regressionPoints.add(new RegressionPoint(new Distance(12), TestUtils.parseInTest("04/02/2115 12:40")));
        RegressionCalculator regressionCalculator = new RegressionCalculator(regressionPoints, configuration.getAlgorithm());

        // when
        double differencesStandardDeviation = regressionCalculator.getDifferencesStandardDeviation(regressionPoints);

        // then
        assertEquals(0.5773, differencesStandardDeviation, 0.001);
    }
}