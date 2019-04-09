
package org.ksapala.rainaproximator.aproximation.weather;

import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.aproximation.debug.RegressionDebug;
import org.ksapala.rainaproximator.aproximation.domainfilters.Filters;
import org.ksapala.rainaproximator.aproximation.regression.RegressionCalculator;
import org.ksapala.rainaproximator.aproximation.regression.RegressionPoint;
import org.ksapala.rainaproximator.aproximation.regression.RegressionResult;
import org.ksapala.rainaproximator.aproximation.regression.RegressionTimeFactory;
import org.ksapala.rainaproximator.aproximation.regression.RegressionPointStructure;
import org.ksapala.rainaproximator.configuration.Configuration;
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
 */
public abstract class Condition {

    private final Logger logger = LoggerFactory.getLogger(Condition.class);

    private static final int REGRESSION_NOT_SET = -1;
	private static final int ZERO_DISTANCE = 0;
	
	private List<Cloud> clouds;

    private double regression = REGRESSION_NOT_SET;
    private double regressionSlope;
    private Boolean regressionForPast = null;

    @Getter private List<Cloud> candidateClouds;
    @Getter private List<Cloud> goodFitClouds;
    @Getter private double rSquare;
    @Getter private double velocity;
    @Getter private double differencesStandardDeviation;
    @Getter private int pointsCount;
    @Getter private RegressionDebug regressionDebug;

    protected Filters filters;

    private RegressionTimeFactory regressionTimeFactory;

    public Condition(List<Cloud> clouds, RegressionTimeFactory regressionTimeFactory, Configuration.Algorithm algorithmConfiguration) {
        this.clouds = clouds;
        this.regressionTimeFactory = regressionTimeFactory;
        this.filters = new Filters(algorithmConfiguration);
    }

    public abstract String getName();
    public abstract RegressionPoint createRegressionPoint(Cloud cloud);
    public abstract List<Cloud> filterCandidates(List<Cloud> clouds);


    public boolean startsInMeantime() {
        return isApproaching() && isRegressionForPast();
    }

    public double getRegression() {
        if (this.regression == REGRESSION_NOT_SET) {
            this.candidateClouds = filterCandidates(clouds);

            List<RegressionPointStructure> structures = candidateClouds.stream()
                    .map(c -> new RegressionPointStructure(createRegressionPoint(c), c))
                    .collect(Collectors.toList());

            structures = filters.filterGoodFit(structures);

            this.goodFitClouds = structures.stream()
                    .map(RegressionPointStructure::getCloud)
                    .collect(Collectors.toList());

            List<RegressionPoint> goodFitPoints = structures.stream()
                    .map(RegressionPointStructure::getRegressionPoint)
                    .collect(Collectors.toList());

            RegressionCalculator calculator = new RegressionCalculator(goodFitPoints);
            RegressionResult result = calculator.calculate(ZERO_DISTANCE);

            this.regression = result.getValue();
            this.regressionSlope  = result.getSlope();
            this.velocity = result.getVelocity();
            this.differencesStandardDeviation = result.getDifferencesStandardDeviation();
            this.rSquare = result.getRSquare();
            this.regressionDebug = result.getRegressionDebug();
            this.pointsCount = result.getPointsCount();
        }
        return this.regression;
    }

    public boolean isRegressionNan() {
        double regression = getRegression();
        return Double.isNaN(regression);
    }

    public boolean isRegressionForPast() {
        if (this.regressionForPast == null) {
            double regression = getRegression();
            LocalDateTime date = TimeUtils.millisToLocalDateAndTime((long) regression);
            this.regressionForPast = date.isBefore(now());
        }
        return this.regressionForPast;
    }

    public boolean isApproaching() {
        getRegression();
        return this.regressionSlope < 0;
    }

    private LocalDateTime now() {
        return this.regressionTimeFactory.now();
    }

    public void log() {
        logger.debug(getName());
        logger.debug("Condition:" + LoggingUtils.getTmeOrNan(getRegression()));
        logger.debug("- is Nan: " + isRegressionNan());
        logger.debug("- is for the past: " + isRegressionForPast());
        logger.debug("Slope:" + regressionSlope);
        logger.debug("- change is approaching:" + isApproaching());

    }

}
