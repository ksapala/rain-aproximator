package org.ksapala.rainaproximator.aproximation;

import org.ksapala.rainaproximator.aproximation.angle.Angle;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLineBuilder;
import org.ksapala.rainaproximator.aproximation.regression.RegressionState;
import org.ksapala.rainaproximator.aproximation.regression.RegressionTimeFactory;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.converter.CoordinatesConverter;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.utils.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class RainAproximator {
	
    private final Logger logger = LoggerFactory.getLogger(RainAproximator.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Configuration configuration;

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
     * @throws AproximationException
     */
	public AproximationResult aproximate(Scan scan, double latitude, double longitude, int angle, String mode)
            throws AproximationException {
		long start = System.currentTimeMillis();

		double[] convert = this.coordinatesConverter.convert(latitude, longitude);
		
		double x = convert[0];
		double y = convert[1];

		AproximationResult aproximatorResult;

        if (Configuration.Algorithm.MODE_AROUND.equals(mode)) {
            aproximatorResult = aproximateAround(scan, x, y).getAproximationResult();

        } else if (Configuration.Algorithm.MODE_AROUND_FINAL.equals(mode)) {
            aproximatorResult = aproximateAroundFinal(scan, x, y);

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
     * @return
     * @throws AproximationException
     */
    private DirectionalAproximationResult aproximateAround(Scan scan, double x, double y) throws AproximationException {
        return aproximateAngles(scan, x, y, algorithmConfiguration.getAroundAngles());
    }

    /**
     *
     * @param scan
     * @param x
     * @param y
     * @return
     * @throws AproximationException
     */
    private AproximationResult aproximateAroundFinal(Scan scan, double x, double y) throws AproximationException {
        DirectionalAproximationResult directionalAproximationResult = aproximateAround(scan, x, y);

        int[] finalAngles = angle.create(directionalAproximationResult.getAngle(), algorithmConfiguration.getAroundFinalAngles());

        DirectionalAproximationResult finalDirectionalAproximationResult = aproximateAngles(scan, x, y, finalAngles);

        return finalDirectionalAproximationResult.getAproximationResult();
    }

    /**
     *
     * @param scan
     * @param x
     * @param y
     * @param angles
     * @return
     * @throws AproximationException
     */
    private DirectionalAproximationResult aproximateAngles(Scan scan, double x, double y, int[] angles) throws AproximationException {
        logger.debug("Aproximating for angles: " + angles);
        List<DirectionalAproximationResult> directionalAproximationResults = new ArrayList<>();

        for (Integer angle : angles) {
            DirectionalAproximationResult result = aproximateStraight(scan, x, y, angle);
            directionalAproximationResults.add(result);
        }

        logDirectionalAproximationResults("Aproximation results to merge:", directionalAproximationResults);
        DirectionalAproximationResult mergedAproximationResult = AproximationResultMerger.merge(directionalAproximationResults);
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
	 * @throws AproximationException
	 */
	private DirectionalAproximationResult aproximateStraight(Scan scan, double x, double y, int angle) throws AproximationException {
        logger.debug("aproximateStraight for angle: " + angle);
		List<CloudLine> cloudLines = this.cloudLineBuilder.createCloudLines(scan, x, y, angle);
		AproximationResult aproximatorResult = aproximate(cloudLines);

        aproximatorResult.getDebug().setCloudLines(cloudLines);
        aproximatorResult.getDebug().setAngle(Double.toString(angle));
		return new DirectionalAproximationResult(angle, aproximatorResult);
	}

	/**
	 * @param cloudLines
	 * @return
	 */
	AproximationResult aproximate(List<CloudLine> cloudLines) {
		smoothCloudLines(cloudLines);

        RegressionState regressionState = new RegressionState(cloudLines, regressionTimeFactory);

        boolean isSun = regressionState.isSun();//predict rain || 0,1
        LoggingUtils.log(regressionState);

        AproximationResult rainAproximation;

		if (isSun) {
			double rainRegression = regressionState.getRainRegression();
			boolean rainRegressionNan = regressionState.isRainRegressionNan();
			boolean rainRegressionForPast = regressionState.isRainRegressionForPast();
			
			if (rainRegressionNan) {
				rainAproximation = new AproximationResult(AproximationResultType.RAIN_UNKNOWN);
			} else if (rainRegressionForPast) {
				rainAproximation = new AproximationResult(AproximationResultType.RAIN_UNSURE);
			} else {
				rainAproximation = new AproximationResult(AproximationResultType.RAIN_AT_TIME, rainRegression);
			}
			
		} else { // rain
			double sunRegression = regressionState.getSunRegression();
			boolean sunRegressionNan = regressionState.isSunRegressionNan();
			boolean sunRegressionForPast = regressionState.isSunRegressionForPast();
			
			if (sunRegressionNan) {
				rainAproximation = new AproximationResult(AproximationResultType.SUN_UNKNOWN);
			} else if (sunRegressionForPast) {
				rainAproximation = new AproximationResult(AproximationResultType.SUN_UNSURE);
			} else {
				rainAproximation = new AproximationResult(AproximationResultType.SUN_AT_TIME, sunRegression);
			}
		}

	    return rainAproximation;
    }

	/**
	 * @param cloudLines
	 */
	public void smoothCloudLines(List<CloudLine> cloudLines) {
	    for (CloudLine cloudLine : cloudLines) {
	        cloudLine.smoothLine();
        }
	    CloudLineBuilder.logCloudLines(logger,"Cloud lines after smooth bellow: ", cloudLines);
    }

}
