/**
 * 
 */
package org.ksapala.rainaproximator.rest;

import org.ksapala.rainaproximator.aproximation.AproximationResult;
import org.ksapala.rainaproximator.aproximation.RainAproximator;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.ScanComponent;
import org.ksapala.rainaproximator.aproximation.wind.WindGetter;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;
import org.ksapala.rainaproximator.rest.factory.AproximationResultBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;


/**
 * @author krzysztof
 *
 */
@Component
public class RainAproximatorService {

    private final Logger logger = LoggerFactory.getLogger(RainAproximatorService.class);

    @Autowired
    private ScanComponent scanComponent;

    @Autowired
    private AproximationResultBeanFactory aproximationResultBeanFactory;

    @Autowired
    private RainAproximator rainAproximator;

    @Autowired
    private Configuration configuration;

    @Autowired
    private WindGetter windGetter;

    public RainAproximatorService() {
    }

    /**
     * @param latitude
     * @param longitude
     * @return
     */
    public AproximationResultBean aproximate(double latitude, double longitude) {
        return aproximate(latitude, longitude, Optional.of(0.0), Optional.empty());
    }

    /**
     * @param latitude
     * @param longitude
     * @param wind
     * @return
     */
    public AproximationResultBean aproximateDebug(double latitude, double longitude, double wind) {
        return aproximate(latitude, longitude, Optional.of(wind), Optional.of(false));
    }

    /**
     * @param latitude
     * @param longitude
     * @param wind
     * @return
     */
    public AproximationResultBean aproximate(double latitude, double longitude, Optional wind, Optional<Boolean> useSideScans) {
        try {
            return doAproximate(latitude, longitude, wind, useSideScans);
        } catch (Exception e) {
            logger.error("Exception while invoking Rain Aproximation Rest Service.", e);
            return aproximationResultBeanFactory.createFriendlyErrorBean();
        }
    }

    /**
     * @param latitude
     * @param longitude
     * @return
     * @throws AproximationException
     * @throws IOException
     */
    public AproximationResultBean doAproximate(double latitude, double longitude, Optional<Double> wind, Optional<Boolean> useSideScans)
            throws AproximationException {
        Optional<Scan> scan = scanComponent.getScan();
        if (!scan.isPresent()) {
            logger.warn("Missing scans.");
            return aproximationResultBeanFactory.createEmptyScanBean();
        }

        Double windDirection = wind
                .orElseGet(() -> windGetter.getWindDirection(latitude, longitude)
                .orElse(configuration.getAlgorithm().getDefaultWind()));

        boolean isUseSideScans = useSideScans
                .orElse(configuration.getAlgorithm().isUseSideScans());

        AproximationResult aproximationResult = rainAproximator.aproximate(scan.get(), latitude, longitude, windDirection, isUseSideScans);
        return aproximationResultBeanFactory.createBean(aproximationResult);
    }

}