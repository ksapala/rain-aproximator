package org.ksapala.rainaproximator.aproximation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLineBuilder;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.aproximation.scan.converter.CoordinatesConverter;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.aproximation.wind.WindGetter;
import org.ksapala.rainaproximator.aproximation.regression.RegressionState;
import org.ksapala.rainaproximator.messages.Messages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RainAproximator {
	
	private final static Logger LOGGER = LogManager.getLogger(RainAproximator.class);

	private final Configuration.Algorithm algorithmConfiguration;
	private CoordinatesConverter coordinatesConverter;
	private CloudLineBuilder cloudLineBuilder;
	private WindGetter windGetter;

	public RainAproximator(Configuration configuration) {
		this.algorithmConfiguration = configuration.getAlgorithm();
		this.coordinatesConverter = new CoordinatesConverter();
		this.cloudLineBuilder = new CloudLineBuilder(algorithmConfiguration.getCloud());
		this.windGetter = new WindGetter(configuration.getWind());
	}
	
	/**
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws IOException
	 * @throws AproximationException 
	 */
	public AproximationResult aproximate(Scan scan, double latitude, double longitude) throws AproximationException {
		long startTime = System.currentTimeMillis();

		double[] convert = this.coordinatesConverter.convert(latitude, longitude);
		
		double x = convert[0];
		double y = convert[1];
		double windDirection = this.windGetter.getWindDirection(latitude, longitude);
		
		AproximationResult aproximatorResult;
		
		if (algorithmConfiguration.isUseSideScans()) {
			aproximatorResult = aproximateWithSideScans(scan, x, y, windDirection);
		} else {
			aproximatorResult = aproximateStraight(scan, x, y, windDirection);
		}

        if (!scan.isCurrentMoment()) {
            aproximatorResult.setRemark(Messages.getString("RainAproximator.radars.not.current"));
        }

		LOGGER.debug("[performance] Aproximation time: " + (System.currentTimeMillis() - startTime));
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
		LOGGER.debug(message);
		for (AproximationResult aproximationResult : aproximationResults) {
			LOGGER.debug(aproximationResult.toString());
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param windDirection
	 * @return
	 * @throws AproximationException
	 */
	private AproximationResult aproximateStraight(Scan scan, double x, double y, double windDirection) throws AproximationException {
		LOGGER.debug("aproximateStraight for direction: " + windDirection);
		List<CloudLine> cloudLines = this.cloudLineBuilder.createCloudLines(scan, x, y, windDirection);
		AproximationResult aproximatorResult = aproximate(cloudLines);
		return aproximatorResult;
	}

	/**
	 * @param cloudLines
	 * @return
	 */
	AproximationResult aproximate(List<CloudLine> cloudLines) {
		smoothCloudLines(cloudLines);
		
		RegressionState regressionState = new RegressionState(cloudLines);
		boolean isSun = regressionState.isSun();//predict rain || 0,1
		
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
	    CloudLineBuilder.logCloudLines("Cloud lines after smooth bellow: ", cloudLines);
    }

}
