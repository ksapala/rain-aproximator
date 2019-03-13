package org.ksapala.rainaproximator.aproximation;

import org.ksapala.rainaproximator.aproximation.angle.Angle;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLineBuilder;
import org.ksapala.rainaproximator.aproximation.debug.Debug;
import org.ksapala.rainaproximator.aproximation.regression.RegressionState;
import org.ksapala.rainaproximator.aproximation.regression.RegressionTimeFactory;
import org.ksapala.rainaproximator.aproximation.result.*;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.converter.CoordinatesConverter;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.configuration.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RainAproximator {
	
    private final Logger logger = LoggerFactory.getLogger(RainAproximator.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Angle angle;

    @Autowired
    private RegressionTimeFactory regressionTimeFactory;

    private final Configuration.Algorithm algorithmConfiguration;
	private CoordinatesConverter coordinatesConverter;
	private CloudLineBuilder cloudLineBuilder;


    @Autowired
    public RainAproximator(Configuration configuration) {
		this.algorithmConfiguration = configuration.getAlgorithm();
		this.coordinatesConverter = new CoordinatesConverter();
		this.cloudLineBuilder = new CloudLineBuilder(algorithmConfiguration.getCloud());
	}

    /**
     *
     * @param scan
     * @param latitude
     * @param longitude
     * @param angle
     * @param mode
     * @return
     */
	public AproximationResult aproximate(Scan scan, double latitude, double longitude, int angle, Mode mode) {
		long start = System.currentTimeMillis();
		double[] convert = this.coordinatesConverter.convert(latitude, longitude);
		
		double x = convert[0];
		double y = convert[1];

		AproximationResult aproximatorResult;

        if (Mode.NAME_AROUND.equals(mode.getName())) {
            aproximatorResult = aproximateAround(scan, x, y, mode).getAproximationResult();

        } else if (Mode.NAME_AROUND_FINAL.equals(mode.getName())) {
            aproximatorResult = aproximateAroundFinal(scan, x, y, mode).getAproximationResult();

        } else { // MODE_STRAIGHT
            aproximatorResult = aproximateStraight(scan, x, y, angle).getAproximationResult();
        }

        if (!scan.isCurrentMoment()) {
            aproximatorResult.setRemark(messageSource.getMessage("RainAproximator.radars.not.current",
                    new Object[0], Locale.getDefault()));
        }

        long end = System.currentTimeMillis();
		long duration = end - start;

		logger.debug("[performance] Aproximation time: " + duration);
        aproximatorResult.getDebug().setPerformance(Long.toString(duration));
		return aproximatorResult;
    }

    /**
     *
     * @param scan
     * @param x
     * @param y
     * @param mode
     * @return
     */
    private DirectionalAproximationResult aproximateAround(Scan scan, double x, double y, Mode mode) {
        return aproximateAngles(scan, mode, x, y, algorithmConfiguration.getAroundAngles());
    }

    /**
     *
     * @param scan
     * @param x
     * @param y
     * @param mode
     * @return
     */
    private DirectionalAproximationResult aproximateAroundFinal(Scan scan, double x, double y, Mode mode) {
        DirectionalAproximationResult directionalAproximationResult = aproximateAround(scan, x, y, mode);

        int[] finalAngles = angle.create(directionalAproximationResult.getAngle(), algorithmConfiguration.getAroundFinalAngles());

        return aproximateAngles(scan, mode, x, y, finalAngles);
    }

    /**
     *
     * @param scan
     * @param mode
     * @param x
     * @param y
     * @param angles
     * @return
     */
    private DirectionalAproximationResult aproximateAngles(Scan scan, Mode mode, double x, double y, int[] angles) {
        logger.debug("Aproximating for angles: " + Arrays.toString(angles));
        List<DirectionalAproximationResult> directionalAproximationResults = new ArrayList<>();

        for (Integer angle : angles) {
            DirectionalAproximationResult result = aproximateStraight(scan, x, y, angle);
            directionalAproximationResults.add(result);
        }

        logDirectionalAproximationResults("Aproximation results to merge:", directionalAproximationResults);
        AproximationResultMerger merger = new AproximationResultMerger(mode);
        DirectionalAproximationResult mergedAproximationResult = merger.merge(directionalAproximationResults);
        logger.debug("Chosed result is: " + mergedAproximationResult.toString());
        return mergedAproximationResult;
    }

    /**
     *
     * @param message
     * @param directionalAproximationResults
     */
	private void logDirectionalAproximationResults(String message, List<DirectionalAproximationResult> directionalAproximationResults) {
        logger.debug(message);
		for (DirectionalAproximationResult directionalAproximationResult : directionalAproximationResults) {
            logger.debug(directionalAproximationResult.toString());
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param angle
	 * @return
	 */
	private DirectionalAproximationResult aproximateStraight(Scan scan, double x, double y, int angle) {
        logger.debug("Step 1 - aproximateStraight for angle: " + angle);

        List<CloudLine> cloudLines = this.cloudLineBuilder.buildCloudLines(scan, x, y, angle);
        logger.debug("Step 2 - created cloud lines bellow:");
        cloudLines.forEach(cloudLine -> logger.debug(cloudLine.toString()));

        logger.debug("Step 3 - cloud lines after smooth bellow:");
        cloudLines.forEach(cloudLine -> {
            cloudLine.smoothLine();
            logger.debug(cloudLine.toString());
        });

        AproximationResult aproximatorResult = aproximate(cloudLines);

        aproximatorResult.getDebug().setAngle(Double.toString(angle));
		return new DirectionalAproximationResult(angle, aproximatorResult);
	}

	/**
	 * @param cloudLines
	 * @return
	 */
	AproximationResult aproximate(List<CloudLine> cloudLines) {
        Debug debug = new Debug();
        debug.setCloudLines(cloudLines);

        RegressionState regressionState = new RegressionState(cloudLines, regressionTimeFactory);

        boolean isSun = regressionState.isSun();
//        LoggingUtils.log(regressionState);

        AproximationResult aproximationResult;

        if (isSun) {
			double rainRegression = regressionState.getRainRegression();
			boolean rainRegressionNan = regressionState.isRainRegressionNan();
			boolean rainRegressionForPast = regressionState.isRainRegressionForPast();

            Accuracy accuracy = new Accuracy(regressionState.getRSquare());
			if (rainRegressionNan) {
				aproximationResult = new AproximationResult(AproximationResultType.RAIN_UNKNOWN, accuracy);
			} else if (rainRegressionForPast) {
				aproximationResult = new AproximationResult(AproximationResultType.RAIN_UNSURE, accuracy);
			} else {
				aproximationResult = new AproximationResult(AproximationResultType.RAIN_AT_TIME, accuracy, rainRegression);
			}

            debug.addInfo(regressionState.getRainCandidates(), "TAKEN");
            debug.addInfo(regressionState.getRainGoodFitClouds(), "GOODFIT");
            debug.setRegressionDebug(regressionState.getRainRegressionDebug());

		} else { // rain
			double sunRegression = regressionState.getSunRegression();
			boolean sunRegressionNan = regressionState.isSunRegressionNan();
			boolean sunRegressionForPast = regressionState.isSunRegressionForPast();

            Accuracy accuracy = new Accuracy(regressionState.getRSquare());
			if (sunRegressionNan) {
				aproximationResult = new AproximationResult(AproximationResultType.SUN_UNKNOWN, accuracy);
			} else if (sunRegressionForPast) {
				aproximationResult = new AproximationResult(AproximationResultType.SUN_UNSURE, accuracy);
			} else {
				aproximationResult = new AproximationResult(AproximationResultType.SUN_AT_TIME, accuracy, sunRegression);
			}

            debug.addInfo(regressionState.getSunCandidates(), "TAKEN");
            debug.addInfo(regressionState.getSunGoodFitClouds(), "GOODFIT");
			debug.setRegressionDebug(regressionState.getSunRegressionDebug());
		}

        aproximationResult.setDebug(debug);
	    return aproximationResult;
    }

}
