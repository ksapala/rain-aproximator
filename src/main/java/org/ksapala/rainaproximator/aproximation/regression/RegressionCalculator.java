/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krzysztof
 *
 */
public class RegressionCalculator {

	private final static Logger LOGGER = LogManager.getLogger(RegressionCalculator.class);
	
	private RegressionDataProvider dataProvider;

	/**
	 * 
	 */
	public RegressionCalculator(RegressionDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public RegressionResult calculate(int x) {
    	List<RegressionPoint> regressionPoints = this.dataProvider.getRegressionPoints();
    	logRegressionPoints(this.dataProvider.getDescription() + " regression points : ", regressionPoints);
    	
    	regressionPoints = removeOutliersDifference(regressionPoints);
    	logRegressionPoints(this.dataProvider.getDescription() + " regression after remove outliners:", regressionPoints);
    	
		double[][] data = toDataArray(regressionPoints);
		
    	SimpleRegression simpleRegression = new SimpleRegression();
    	simpleRegression.addData(data);

		double regression = simpleRegression.predict(x);
		double regressionSlope  = simpleRegression.getSlope();
		
    	RegressionResult result = new RegressionResult(regression, regressionSlope);
		return result;
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
			data[i][1] = regressionPoint.getDate().getTime();
		}
		return data;
	}

	/**
	 * @param message
	 * @param regressionPoints
	 */
	private void logRegressionPoints(String message, List<RegressionPoint> regressionPoints) {
		LOGGER.debug(message);
		for (RegressionPoint regressionPoint : regressionPoints) {
			LOGGER.debug("Point distance={}, date={}", regressionPoint.getDistance().getValue(), regressionPoint.getDate());
		}
	}

	/**
	 * @param regressionPoints
	 * @return
	 */
	public List<RegressionPoint> removeOutliersDifference(List<RegressionPoint> regressionPoints) {
		if (regressionPoints.isEmpty()) {
			return regressionPoints;
		}
		List<RegressionPoint> result = new ArrayList<RegressionPoint>();
		double[] differences = getDifferences(regressionPoints);
		
		double median = getMedian(differences);
		double borderValue = regressionPoints.get(0).getDistance().getValue() - 2 * median;
		boolean add = true;
		
		for (RegressionPoint regressionPoint : regressionPoints) {
			Distance distance = regressionPoint.getDistance();
			int value = distance.getValue();
			
			if (median > 0) {
				add = value >= borderValue;
			} else {
				add = value <= borderValue;
			}
			
			if (add) {
				result.add(regressionPoint);
			}

			borderValue = borderValue + median;
			
		}
		return result;
	}
	
	/**
	 * @param regressionPoints
	 * @return
	 */
	private double[] getDifferences(List<RegressionPoint> regressionPoints) {
		if (regressionPoints.isEmpty()) {
			return new double[0];
		}
		double[] differences = new double[regressionPoints.size() - 1];
		for (int i = 0; i < regressionPoints.size() - 1; i++) {
			RegressionPoint regressionPoint = regressionPoints.get(i);
			RegressionPoint regressionPointNext = regressionPoints.get(i + 1);
			
			double value = regressionPoint.getDistance().getValue();
			double valueNext = regressionPointNext.getDistance().getValue();
			
			differences[i] = valueNext - value;
		}
		return differences;
	}

//	/**
//	 * @param regressionPoints
//	 * @return
//	 */
//	@Deprecated
//	public static List<RegressionPoint> removeOutliers(List<RegressionPoint> regressionPoints) {
//		List<RegressionPoint> result = new ArrayList<RegressionPoint>();
//
//		double median = getMedian(regressionPoints);
//		int outliersRange = Settings.getInt(Property.OUTLIERS_RANGE);
//
//		for (RegressionPoint regressionPoint : regressionPoints) {
//			Distance distance = regressionPoint.getDistance();
//			int value = distance.getValue();
//			int divide = (int) (value/median * 100);
//			int outlierDistance = Math.abs(divide - 100);
//			if (outlierDistance < outliersRange) { // outlier in range
//				result.add(regressionPoint);
//			}
//		}
//		return result;
//	}

	private double getMedian(double[] numbers) {
		DescriptiveStatistics statistics = new DescriptiveStatistics();
		for (Double number : numbers) {
			statistics.addValue(number);
		}
		
		double median = statistics.getPercentile(50);
		return median;
	}
	
	private static double getMedian(List<RegressionPoint> regressionPoints) {
		DescriptiveStatistics statistics = new DescriptiveStatistics();
		for (RegressionPoint regressionPoint : regressionPoints) {
			Distance distance = regressionPoint.getDistance();
			statistics.addValue(distance.getValue());
		}
		
		double median = statistics.getPercentile(50);
		return median;
	}
	
}
