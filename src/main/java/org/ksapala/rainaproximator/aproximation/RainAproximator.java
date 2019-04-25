package org.ksapala.rainaproximator.aproximation;

import org.ksapala.rainaproximator.aproximation.angle.Angle;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.aproximation.cloud.CloudBuilder;
import org.ksapala.rainaproximator.aproximation.debug.Debug;
import org.ksapala.rainaproximator.aproximation.regression.RegressionTimeFactory;
import org.ksapala.rainaproximator.aproximation.result.*;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.converter.CoordinatesConverter;
import org.ksapala.rainaproximator.aproximation.weather.Condition;
import org.ksapala.rainaproximator.aproximation.weather.Weather;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.configuration.Mode;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

@Component
public class RainAproximator {
	
    private final Logger logger = LoggerFactory.getLogger(RainAproximator.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RegressionTimeFactory regressionTimeFactory;

    private final Configuration.Algorithm algorithmConfiguration;
	private CoordinatesConverter coordinatesConverter;
	private CloudBuilder cloudBuilder;


    @Autowired
    public RainAproximator(Configuration configuration) {
		this.algorithmConfiguration = configuration.getAlgorithm();
		this.coordinatesConverter = new CoordinatesConverter();
		this.cloudBuilder = new CloudBuilder(algorithmConfiguration.getCloud());
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
	public Aproximation aproximate(Scan scan, double latitude, double longitude, int angle, Mode mode) {
        logger.info("Aproximation start. Scan: " + scan.getLastMapTime() + " Location: (" + latitude * 100 / 100 + ", " +
                + longitude * 100 / 100 + ") Angle: " + angle + " Mode: " + mode.getName());

		long start = System.currentTimeMillis();
		double[] convert = this.coordinatesConverter.convert(latitude, longitude);
		
		double x = convert[0];
		double y = convert[1];

		Aproximation aproximation;

        if (Mode.NAME_FULL.equals(mode.getName())) {
            aproximation = aproximateFull(scan, x, y, mode);

        } else if (Mode.NAME_AROUND.equals(mode.getName())) {
            aproximation = aproximateAround(scan, x, y, mode);

        } else if (Mode.NAME_AROUND_FINAL.equals(mode.getName())) {
            aproximation = aproximateAroundFinal(scan, x, y, mode);

        } else { // MODE_STRAIGHT
            aproximation = aproximateStraight(scan, x, y, angle);
        }

        if (!scan.isCurrentMoment()) {
            aproximation.getAproximationResult().setRemark(messageSource.getMessage("RainAproximator.radars.not.current",
                    new Object[0], Locale.getDefault()));
        }

        long end = System.currentTimeMillis();
		long duration = end - start;

		logger.debug("[performance] Aproximation time: " + duration);
        aproximation.getAproximationResult().getDebug().setPerformance(Long.toString(duration));
		return aproximation;
    }

    /**
     *
     * @param scan
     * @param x
     * @param y
     * @param mode
     * @return
     */
    private Aproximation aproximateFull(Scan scan, double x, double y, Mode mode) {
	    int[] fullAngles = IntStream.range(0, 360).toArray();
        return aproximateAngles(scan, mode, x, y, fullAngles);
    }


    /**
     *
     * @param scan
     * @param x
     * @param y
     * @param mode
     * @return
     */
    private Aproximation aproximateAround(Scan scan, double x, double y, Mode mode) {
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
    private Aproximation aproximateAroundFinal(Scan scan, double x, double y, Mode mode) {
        Aproximation aproximation = aproximateAround(scan, x, y, mode);

        int[] finalAngles = Angle.create(aproximation.getAngle(), algorithmConfiguration.getAroundFinalAngles());

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
    private Aproximation aproximateAngles(Scan scan, Mode mode, double x, double y, int[] angles) {
        logger.debug("Aproximating for angles: " + Arrays.toString(angles));
        List<Aproximation> aproximation = new ArrayList<>();

        for (Integer angle : angles) {
            Aproximation result = aproximateStraight(scan, x, y, angle);
            aproximation.add(result);
        }

        logaproximation("Aproximation results to merge:", aproximation);
        AproximationResultMerger merger = new AproximationResultMerger(mode);
        Aproximation mergedAproximation = merger.merge(aproximation);
        logger.debug("Chosed result is: " + mergedAproximation.toString());
        return mergedAproximation;
    }

    /**
     *
     * @param message
     * @param aproximation
     */
	private void logaproximation(String message, List<Aproximation> aproximation) {
        logger.debug(message);
		for (Aproximation directionalAproximationResult : aproximation) {
            logger.debug(directionalAproximationResult.toString());
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param angle
	 * @return
	 */
	private Aproximation aproximateStraight(Scan scan, double x, double y, int angle) {
        logger.debug("Step 1 - aproximateStraight for angle: " + angle);

        List<Cloud> clouds = this.cloudBuilder.buildClouds(scan, x, y, angle);
        logger.debug("Step 2 - created clouds lines bellow:");
        clouds.forEach(cloud -> logger.debug(cloud.toString()));

        logger.debug("Step 3 - clouds lines after smooth bellow:");
        clouds.forEach(cloud -> {
            cloud.smoothLine();
            logger.debug(cloud.toString());
        });

        return aproximate(clouds, angle);
    }

    /**
     *
     * @param clouds
     * @param angle
     * @return
     */
	Aproximation aproximate(List<Cloud> clouds, int angle) {
        Weather weather = new Weather(clouds, regressionTimeFactory, algorithmConfiguration);

        boolean isSun = weather.isSun();

//        LoggingUtils.log(weather);

        AproximationResult aproximationResult;
        Condition condition;
        if (isSun) {
            condition = weather.getRain();
        } else {
            condition = weather.getSun();
        }
        double regression = condition.getRegression();
        boolean regressionNan = condition.isRegressionNan();
        boolean regressionForPast = condition.isRegressionForPast();

        if (isSun) {
			if (regressionNan) {
				aproximationResult = new AproximationResult(AproximationResultType.RAIN_UNKNOWN);
			} else if (regressionForPast) {
				aproximationResult = new AproximationResult(AproximationResultType.RAIN_UNSURE);
			} else {
				aproximationResult = new AproximationResult(AproximationResultType.RAIN_AT_TIME, regression);
			}

		} else { // rain
			if (regressionNan) {
				aproximationResult = new AproximationResult(AproximationResultType.SUN_UNKNOWN);
			} else if (regressionForPast) {
				aproximationResult = new AproximationResult(AproximationResultType.SUN_UNSURE);
			} else {
				aproximationResult = new AproximationResult(AproximationResultType.SUN_AT_TIME, regression);
			}
		}

        Accuracy accuracy = Accuracy.of(condition.getRSquare(), angle, condition.getVelocity(),
                condition.getDifferencesStandardDeviation(), condition.getPointsCount());

        Debug debug = new Debug();
        debug.setGenerationTime(TimeUtils.timeToString(LocalDateTime.now(), Debug.TIME_FORMAT));
        debug.setClouds(clouds);
        debug.setAngle(Double.toString(angle));
        debug.addInfo(condition.getCandidateClouds(), "TAKEN");
        debug.addInfo(condition.getGoodFitClouds(), "GOODFIT");
        debug.setRegressionDebug(condition.getRegressionDebug());

        aproximationResult.setDebug(debug);
	    return new Aproximation(angle, aproximationResult, accuracy);
    }

}
