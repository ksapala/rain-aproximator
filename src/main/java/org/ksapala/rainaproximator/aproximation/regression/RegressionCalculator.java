
package org.ksapala.rainaproximator.aproximation.regression;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.ksapala.rainaproximator.aproximation.debug.RegressionDebug;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * Finds line distance(time) for given time.
 * Time is x.
 * Regression result is line f(x).
 *
 * @author krzysztof
 *
 */
public class RegressionCalculator {

    private final Logger logger = LoggerFactory.getLogger(RegressionCalculator.class);

    private List<RegressionPoint> points;
    private Configuration.Algorithm algorithmConfiguration;

    /**
	 *
     * @param points
     */
	public RegressionCalculator(List<RegressionPoint> points, Configuration.Algorithm algorithmConfiguration) {
        this.points = points;
        this.algorithmConfiguration = algorithmConfiguration;
    }

    /**
     * Calculates regression.
     *
     * @param x
     * @return
     */
	public RegressionResult calculate(int x) {
	    if (points.isEmpty()) {
	        logger.debug("Regression skipped - empty data.");
            return RegressionResult.NAN_RESULT;
        }

		double[][] data = toDataArray(points);
		
    	SimpleRegression simpleRegression = new SimpleRegression();
    	simpleRegression.addData(data);

		double regression = simpleRegression.predict(x);
		double regressionSlope  = simpleRegression.getSlope();
        double standardDeviation = getStandardDeviation(points);
        double differencesStandardDeviation = getDifferencesStandardDeviation(points);
        double rSquare = simpleRegression.getRSquare();
        double velocity = regressionSlope / 1000 / 60;

        RegressionDebug regressionDebug = RegressionDebug.of(standardDeviation, differencesStandardDeviation, regressionSlope, rSquare, velocity, points.size());
        return new RegressionResult(regression, regressionSlope, rSquare, velocity, differencesStandardDeviation, points.size(), regressionDebug);
	}

    /**
     * @param regressionPoints
     * @return
     */
    private double getStandardDeviation(List<RegressionPoint> regressionPoints) {
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        regressionPoints.forEach(p -> statistics.addValue(p.getDistance().getValue()));
        return statistics.getStandardDeviation();
    }

    /**
     * @param regressionPoints
     * @return
     */
    double getDifferencesStandardDeviation(List<RegressionPoint> regressionPoints) {
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        int[] differences = IntStream.range(0, regressionPoints.size() - 1)
                .map(new CountDifferenceStandardDeviation(regressionPoints))
                .toArray();

        Arrays.stream(differences).forEach(statistics::addValue);
        return statistics.getStandardDeviation();
    }

    /**
     *
     */
    class CountDifferenceStandardDeviation implements IntUnaryOperator {

        private List<RegressionPoint> regressionPoints;

        public CountDifferenceStandardDeviation(List<RegressionPoint> regressionPoints) {
            this.regressionPoints = regressionPoints;
        }

        @Override
        public int applyAsInt(int operand) {
            RegressionPoint regressionPoint1 = regressionPoints.get(operand);
            RegressionPoint regressionPoint2 = regressionPoints.get(operand + 1);

            int differenceMinutes = (int) Duration.between(regressionPoint1.getTime(), regressionPoint2.getTime()).toMinutes();
            int pointsTimeFactor = differenceMinutes / algorithmConfiguration.getRadarMapTimeIntevalMinutes();

            return (regressionPoint2.getDistance().getValue() - regressionPoint1.getDistance().getValue()) / pointsTimeFactor;
        }
    }

    /**
	 * @param regressionPoints
	 * @return
	 */
	private double[][] toDataArray(List<RegressionPoint> regressionPoints) {
		double[][] data = new double[regressionPoints.size()][2];
		for (int i = 0; i < regressionPoints.size(); i++) {
			RegressionPoint regressionPoint = regressionPoints.get(i);
			data[i][0] = regressionPoint.getDistance().getValue();
			data[i][1] = TimeUtils.dateToMillis(regressionPoint.getTime());
		}
		return data;
	}


	/**
	 * @param message
	 * @param regressionPoints
	 */
	private void logRegressionPoints(String message, List<RegressionPoint> regressionPoints) {
		logger.trace(message);
		for (RegressionPoint regressionPoint : regressionPoints) {
			logger.trace("Point distance={}, date={}", regressionPoint.getDistance().getValue(), regressionPoint.getTime());
		}
	}

}
