
package org.ksapala.rainaproximator.rest.service;

import org.ksapala.rainaproximator.aproximation.RainAproximator;
import org.ksapala.rainaproximator.aproximation.result.Aproximation;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.ScanComponent;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.configuration.Mode;
import org.ksapala.rainaproximator.rest.answer.AproximationAnswer;
import org.ksapala.rainaproximator.rest.answer.AproximationAnswerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


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
    private AproximationAnswerFactory aproximationAnswerFactory;

    @Autowired
    private RainAproximator rainAproximator;

    @Autowired
    private Configuration configuration;

    public RainAproximatorService() {
    }

    /**
     * @param latitude
     * @param longitude
     * @return
     */
    public AproximationAnswer aproximate(double latitude, double longitude) {
        return aproximate(latitude, longitude, 0, null);
    }

    /**
     * @param latitude
     * @param longitude
     * @param angle
     * @param mode
     * @return
     */
    public AproximationAnswer aproximateDebug(double latitude, double longitude, int angle, Mode mode) {
        return aproximate(latitude, longitude, angle, mode);
    }

    /**
     * @param latitude
     * @param longitude
     * @param angle
     * @param mode
     * @return
     */
    public AproximationAnswer aproximate(double latitude, double longitude, int angle, Mode mode) {
        try {
            return doAproximate(latitude, longitude, angle, mode);
        } catch (Exception e) {
            logger.error("Exception while invoking Rain Aproximation Rest Service.", e);
            return aproximationAnswerFactory.createFriendlyErrorBean();
        }
    }

    /**
     * @param latitude
     * @param longitude
     * @param angle
     * @param mode
     * @return
     */
    public AproximationAnswer doAproximate(double latitude, double longitude, int angle, Mode mode) {

        Optional<Scan> scan = scanComponent.getScan();

        if (!scan.isPresent()) {
            logger.warn("Missing scans.");
            return aproximationAnswerFactory.createEmptyScanBean();
        }

        Mode aproximationMode = Optional.ofNullable(mode)
                .orElse(new Mode(configuration.getAlgorithm().getMode()));

        Aproximation aproximation = rainAproximator.aproximate(scan.get(), latitude, longitude, angle, aproximationMode);
        return aproximationAnswerFactory.createAproximationAnswer(aproximation);
    }

}