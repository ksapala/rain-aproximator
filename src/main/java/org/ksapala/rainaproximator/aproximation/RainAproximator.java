package org.ksapala.rainaproximator.aproximation;

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
     * @param wind
     * @param useSideScans
     * @return
     * @throws AproximationException
     */
	public AproximationResult aproximate(Scan scan, double latitude, double longitude, double wind, boolean useSideScans)
            throws AproximationException {
		long start = System.currentTimeMillis();

		double[] convert = this.coordinatesConverter.convert(latitude, longitude);
		
		double x = convert[0];
		double y = convert[1];

		AproximationResult aproximatorResult;

		if (useSideScans) {
			aproximatorResult = aproximateWithSideScans(scan, x, y, wind);
		} else {
			aproximatorResult = aproximateStraight(scan, x, y, wind);
		}

        if (!scan.isCurrentMoment()) {
            aproximatorResult.setRemark(messageSource.getMessage("RainAproximator.radars.not.current",
                    new Object[0], Locale.getDefault()));
        }

        long end = System.currentTimeMillis();
		logger.debug("[performance] Aproximation time: " + (end - start));
		return aproximatorResult;
    }

	/**
	 * @param x
	 * @param y
	 * @param windDirection
	 * @return
	 * @throws AproximationException
	 */
	private AproximationResult aproximateWithSideScans(Scan scan, double x, double y, double windDirection) throws AproximationException {
		List<AproximationResult> aproximationResults = new ArrayList<AproximationResult>();

		// straight
		AproximationResult straightAproximationResult = aproximateStraight(scan, x, y, windDirection);
		aproximationResults.add(straightAproximationResult);
		
		// side scans
		for (Integer sideScansAngle : algorithmConfiguration.getSideScansAngles()) {
			double scanAngle = windDirection + sideScansAngle;
			AproximationResult sideScanAproximationResult = aproximateStraight(scan, x, y, scanAngle);
			aproximationResults.add(sideScanAproximationResult);
		}
		
		logAproximationResults("Aproximation results to merge:", aproximationResults);
		AproximationResult mergedAproximationResult = AproximationResultMerger.merge(aproximationResults);
		return mergedAproximationResult;
	}

	private void logAproximationResults(String message, List<AproximationResult> aproximationResults) {
        logger.debug(message);
		for (AproximationResult aproximationResult : aproximationResults) {
            logger.debug(aproximationResult.toString());
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param wind
	 * @return
	 * @throws AproximationException
	 */
	private AproximationResult aproximateStraight(Scan scan, double x, double y, double wind) throws AproximationException {
        logger.debug("aproximateStraight for wind direction: " + wind);
		List<CloudLine> cloudLines = this.cloudLineBuilder.createCloudLines(scan, x, y, wind);
		AproximationResult aproximatorResult = aproximate(cloudLines);

        aproximatorResult.getDebug().setCloudLines(cloudLines);
        aproximatorResult.getDebug().setWind(Double.toString(wind));
		return aproximatorResult;
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
