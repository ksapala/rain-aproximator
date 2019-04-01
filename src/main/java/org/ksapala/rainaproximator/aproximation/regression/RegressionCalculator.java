
package org.ksapala.rainaproximator.aproximation.regression;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.ksapala.rainaproximator.aproximation.debug.RegressionDebug;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

    /**
	 *
     * @param points
     */
	public RegressionCalculator(List<RegressionPoint> points) {
        this.points = points;
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

        RegressionDebug regressionDebug = RegressionDebug.of(standardDeviation, differencesStandardDeviation, regressionSlope, rSquare, velocity, points);
        return new RegressionResult(regression, regressionSlope, rSquare, velocity, differencesStandardDeviation, regressionDebug);
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
                .map(i -> regressionPoints.get(i + 1).getDistance().getValue() - regressionPoints.get(i).getDistance().getValue())
                .toArray();

        Arrays.stream(differences).forEach(statistics::addValue);
        return statistics.getStandardDeviation();
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
			data[i][1] = TimeUtils.localDateAndTimeToMillis(regressionPoint.getTime());
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
