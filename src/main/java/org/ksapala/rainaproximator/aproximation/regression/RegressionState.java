/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.regression;

import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.domainfilters.Filters;
import org.ksapala.rainaproximator.aproximation.debug.RegressionDebug;
import org.ksapala.rainaproximator.aproximation.structure.RegressionPointStructure;
import org.ksapala.rainaproximator.utils.LoggingUtils;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author krzysztof
 *
 * Changes cloud lines into information about sun/rain regression.
 *
 * contact: krzysztof.sapala@gmail.com
 *
 *
 * TODO: Refactor this: Reuse code for rain and sun + make this bean.
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


    @Getter private List<CloudLine> rainCandidates;
    @Getter private List<CloudLine> sunCandidates;
    @Getter private List<CloudLine> rainGoodFitClouds;
    @Getter private List<CloudLine> sunGoodFitClouds;
    @Getter private double rSquare;
    @Getter private RegressionDebug rainRegressionDebug;
    @Getter private RegressionDebug sunRegressionDebug;

    private Filters filters;

    public  RegressionState(List<CloudLine> cloudLines, RegressionTimeFactory regressionTimeFactory) {
        this.cloudLines = cloudLines;
        this.regressionTimeFactory = regressionTimeFactory;
        this.filters = new Filters();
    }

    /**
     * @return
     */
    public boolean isSun() {
        if (CloudLine.isSun(cloudLines)) {
            return !rainInMeantime();
        } else {
            return sunInMeantime();
        }
    }

    /**
     * @return
     */
    private boolean rainInMeantime() {
        return rainIsApproaching() && isRainRegressionForPast();
    }

    /**
     * @return
     */
    private boolean sunInMeantime() {
        return sunIsApproaching() && isSunRegressionForPast();
    }

    /**
	 * @return
	 */
	public double getRainRegression() {
	    if (this.rainRegression == REGRESSION_NOT_SET) {
            this.rainCandidates = filters.filterRainCandidates(cloudLines);

            List<RegressionPointStructure> structures = rainCandidates.stream()
                    .map(c -> new RegressionPointStructure(new RegressionPoint(c.getFutureRainDistance(), c.getTime()), c))
                    .collect(Collectors.toList());

            structures = filters.filterGoodFit(structures);

            this.rainGoodFitClouds = structures.stream()
                    .map(RegressionPointStructure::getCloudLine)
                    .collect(Collectors.toList());

            List<RegressionPoint> goodFitPoints = structures.stream()
                    .map(RegressionPointStructure::getRegressionPoint)
                    .collect(Collectors.toList());

            RegressionCalculator calculator = new RegressionCalculator(goodFitPoints);
            RegressionResult result = calculator.calculate(ZERO_DISTANCE);

			this.rainRegression = result.getValue();
			this.rainRegressionSlope  = result.getSlope();
            this.rSquare = result.getRSquare();
            this.rainRegressionDebug = result.getRegressionDebug();
        }
	    return this.rainRegression;
    }
	
	/**
	 * @return
	 */
	public double getSunRegression() {
		if (this.sunRegression == REGRESSION_NOT_SET) {
            this.sunCandidates = filters.filterSunCandidates(cloudLines);

            List<RegressionPointStructure> structures = sunCandidates.stream()
                    .map(c -> new RegressionPointStructure(new RegressionPoint(c.getFutureSunDistance(), c.getTime()), c))
                    .collect(Collectors.toList());

            structures = filters.filterGoodFit(structures);

            this.sunGoodFitClouds = structures.stream()
                    .map(RegressionPointStructure::getCloudLine)
                    .collect(Collectors.toList());

            List<RegressionPoint> goodFitPoints = structures.stream()
                    .map(RegressionPointStructure::getRegressionPoint)
                    .collect(Collectors.toList());

            RegressionCalculator calculator = new RegressionCalculator(goodFitPoints);
            RegressionResult result = calculator.calculate(ZERO_DISTANCE);

            this.sunRegression = result.getValue();
			this.sunRegressionSlope  = result.getSlope();
            this.rSquare = result.getRSquare();
            this.sunRegressionDebug = result.getRegressionDebug();
	    }
	    return this.sunRegression;
    }

	/**
	 * @return
	 */
	public boolean isRainRegressionNan() {
		double rainRegression = getRainRegression();
        return Double.isNaN(rainRegression);
	}
	
	/**
	 * @return
	 */
	public boolean isSunRegressionNan() {
		double sunRegression = getSunRegression();
        return Double.isNaN(sunRegression);
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
	public boolean sunIsApproaching() {
	    getSunRegression();
        return this.sunRegressionSlope < 0;
	}
	
	/**
	 * @return
	 */
	public boolean rainIsApproaching() {
	    getRainRegression();
        return this.rainRegressionSlope < 0;
	}

    private LocalDateTime now() {
        return this.regressionTimeFactory.now();
    }

    public void log() {
	    if (isSun()) {
	        logger.debug("Sun (......)");
            logger.debug("Rain regression :" + LoggingUtils.getTmeOrNan(getRainRegression()));
            logger.debug("- is Nan: " + isRainRegressionNan());
            logger.debug("- is for the past: " + isRainRegressionForPast());
            logger.debug("Slope:" + rainRegressionSlope);
            logger.debug("- rain is approaching:" + rainIsApproaching());
        } else {
            logger.debug("Rain (#######)");
            logger.debug("Sun regression: " + LoggingUtils.getTmeOrNan(getSunRegression()));
            logger.debug("- is Nan: " + isSunRegressionNan());
            logger.debug("- is for the past: " + isSunRegressionForPast());
            logger.debug("Slope:" + sunRegressionSlope);
            logger.debug("- sun is approaching:" + sunIsApproaching());
        }
    }

}
