/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;
import org.ksapala.rainaproximator.settings.Settings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author krzysztof
 *
 * contact: krzysztof.sapala@gmail.com
 *
 */
public class RegressionState {

	
	private static final int REGRESSION_NOT_SET = -1;
	private static final int ZERO_DISTANCE = 0;
	
	private List<CloudLine> cloudLines;
	private double rainRegression = REGRESSION_NOT_SET;
	private double sunRegression = REGRESSION_NOT_SET;
	private double rainRegressionSlope;
	private double sunRegressionSlope;
	private Boolean rainRegressionForPast = null;
	private Boolean sunRegressionForPast = null;
	
	/**
	 * 
	 */
	public RegressionState(List<CloudLine> cloudLines) {
		this.cloudLines = cloudLines;
	}

	/**
	 * @return
	 */
	public boolean isSun() {
		boolean rainRegressionNan = isRainRegressionNan();
		if (!rainRegressionNan) {
			return true;
		}

		boolean sunRegressionNan = isSunRegressionNan();
		if (!sunRegressionNan) {
			boolean rainDecrease = rainDecrease();
			boolean sunRegressionForPast = isSunRegressionForPast();
			return  rainDecrease && sunRegressionForPast;
		}

		List<Distance> rainDistances = getRainInfinityDistances();
		List<Distance> sunDistances = getSunInfinityDistances();
	    boolean moreInfiniotyRainDistances = rainDistances.size() > sunDistances.size();
		return moreInfiniotyRainDistances;
    }

	/**
	 * @return
	 */
	private List<Distance> getRainInfinityDistances() {
		List<Distance> result = new ArrayList<Distance>();
	    for (CloudLine cloudLine : this.cloudLines) {
	        Distance rainDistance = cloudLine.getRainDistance();
			if (Distance.INFINITY.equals(rainDistance)) {
	        	result.add(rainDistance);
	        }
        }
	    return result;
    }
	
	/**
	 * @return
	 */
	private List<Distance> getSunInfinityDistances() {
		List<Distance> result = new ArrayList<Distance>();
	    for (CloudLine cloudLine : this.cloudLines) {
	        Distance sunDistance = cloudLine.getSunDistance();
			if (Distance.INFINITY.equals(sunDistance)) {
	        	result.add(sunDistance);
	        }
        }
	    return result;
    }

	/**
	 * @return
	 */
	public double getRainRegression() {
	    if (this.rainRegression == REGRESSION_NOT_SET) {
	    	RainRegressionDataProvider dataProvider = new RainRegressionDataProvider(this.cloudLines);
			RegressionCalculator calculator = new RegressionCalculator(dataProvider);
	    	RegressionResult result = calculator.calculate(ZERO_DISTANCE);
	    	
			this.rainRegression = result.getValue();
			this.rainRegressionSlope  = result.getSlope();
	    }
	    return this.rainRegression;
    }
	
	/**
	 * @return
	 */
	public double getSunRegression() {
		if (this.sunRegression == REGRESSION_NOT_SET) {
			SunRegressionDataProvider dataProvider = new SunRegressionDataProvider(this.cloudLines);
			RegressionCalculator calculator = new RegressionCalculator(dataProvider);
	    	RegressionResult result = calculator.calculate(ZERO_DISTANCE);
	    	
			this.sunRegression = result.getValue();
			this.sunRegressionSlope  = result.getSlope();
	    }
	    return this.sunRegression;
    }

	/**
	 * @return
	 */
	public boolean isRainRegressionNan() {
		double rainRegression = getRainRegression();
		boolean isRainRegressionNan = Double.isNaN(rainRegression);
		return isRainRegressionNan;
	}
	
	/**
	 * @return
	 */
	public boolean isSunRegressionNan() {
		double sunRegression = getSunRegression();
		boolean isSunRegressionNan = Double.isNaN(sunRegression);
		return isSunRegressionNan;
	}
	
	/**
	 * @return
	 */
	public boolean isRainRegressionForPast() {
		if (this.rainRegressionForPast == null) {
			double rainRegression = getRainRegression();
			Date rainDate = new Date((long) rainRegression);
			Date now = getNow();
			this.rainRegressionForPast = rainDate.before(now);
		}
	    return this.rainRegressionForPast;
    }
	
	/**
	 * @return
	 */
	public boolean isSunRegressionForPast() {
		if (this.sunRegressionForPast == null) {
			double sunRegression = getSunRegression();
			Date sunDate = new Date((long) sunRegression);
			Date now = getNow();
			this.sunRegressionForPast = sunDate.before(now);
		}
	    return this.sunRegressionForPast;
    }
	
	/**
	 * @return
	 */
	public boolean rainDecrease() {
		boolean rainDecrease = this.sunRegressionSlope < 0;
		return rainDecrease;
	}
	
	/**
	 * @return
	 */
	public boolean sunDecrease() {
		boolean sunDecrease = this.rainRegressionSlope > 0;
		return sunDecrease;
	}


	private Date getNow() {
	    return Settings.getNow();
    }


}
