/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.cloud.CloudsOperations;
import org.ksapala.rainaproximator.utils.LoggingUtils;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author krzysztof
 *
 * contact: krzysztof.sapala@gmail.com
 *
 */
public class RegressionState {

    private final Logger logger = LoggerFactory.getLogger(RegressionState.class);

    private static final int REGRESSION_NOT_SET = -1;
	private static final int ZERO_DISTANCE = 0;
	
	private List<CloudLine> cloudLines;
    private RegressionTimeFactory regressionTimeFactory;

    private double rainRegression = REGRESSION_NOT_SET;
	private double sunRegression = REGRESSION_NOT_SET;
	private double rainRegressionSlope;
	private double sunRegressionSlope;
	private Boolean rainRegressionForPast = null;
	private Boolean sunRegressionForPast = null;

    public RegressionState(List<CloudLine> cloudLines, RegressionTimeFactory regressionTimeFactory) {
        this.cloudLines = cloudLines;
        this.regressionTimeFactory = regressionTimeFactory;
    }

    /**
     * @return
     */
    public boolean isSun() {
        return CloudsOperations.isSun(cloudLines);
    }

	/**
	 * @return
	 */
	public double getRainRegression() {
	    if (this.rainRegression == REGRESSION_NOT_SET) {
            List<CloudLine> forRainRegression = CloudsOperations.filterForRainRegression(cloudLines);
            RainRegressionDataProvider dataProvider = new RainRegressionDataProvider(forRainRegression);
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
            List<CloudLine> forSunRegression = CloudsOperations.filterForSunRegression(cloudLines);
			SunRegressionDataProvider dataProvider = new SunRegressionDataProvider(forSunRegression);
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
			LocalDateTime rainDate = TimeUtils.millisToLocalDateAndTime((long) rainRegression);
			this.rainRegressionForPast = rainDate.isBefore(now());
		}
	    return this.rainRegressionForPast;
    }
	
	/**
	 * @return
	 */
	public boolean isSunRegressionForPast() {
		if (this.sunRegressionForPast == null) {
			double sunRegression = getSunRegression();
            LocalDateTime sunDate = TimeUtils.millisToLocalDateAndTime((long) sunRegression);
			this.sunRegressionForPast = sunDate.isBefore(now());
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
		boolean sunDecrease = this.rainRegressionSlope < 0;
		return sunDecrease;
	}

    public LocalDateTime now() {
        return this.regressionTimeFactory.now();
    }

    public void log() {
	    if (isSun()) {
	        logger.debug("Sun (......)");
            logger.debug("Rain regression :" + LoggingUtils.getTmeOrNan(getRainRegression()));
            logger.debug("- is Nan: " + isRainRegressionNan());
            logger.debug("- is for the past: " + isRainRegressionForPast());
            logger.debug("Slope:" + rainRegressionSlope);
            logger.debug("- sun decrease:" + sunDecrease());
        } else {
            logger.debug("Rain (#######)");
            logger.debug("Sun regression: " + LoggingUtils.getTmeOrNan(getSunRegression()));
            logger.debug("- is Nan: " + isSunRegressionNan());
            logger.debug("- is for the past: " + isSunRegressionForPast());
            logger.debug("Slope:" + sunRegressionSlope);
            logger.debug("- rain decrease:" + rainDecrease());
        }
    }
}
