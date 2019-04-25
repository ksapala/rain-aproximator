/**
 * 
 */
package org.ksapala.rainaproximator.rest.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.ksapala.rainaproximator.aproximation.RainAproximator;
import org.ksapala.rainaproximator.aproximation.result.Aproximation;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.ScanComponent;
import org.ksapala.rainaproximator.aproximation.wind.WindGetter;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.configuration.Mode;
import org.ksapala.rainaproximator.rest.bean.AproximationBean;
import org.ksapala.rainaproximator.rest.bean.Bu;
import org.ksapala.rainaproximator.rest.factory.AproximationBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;


/**
 * @author krzysztof
 *
 * // TODO refactor optionals
 */
@Component
public class RainAproximatorService {

    private final Logger logger = LoggerFactory.getLogger(RainAproximatorService.class);

    @Autowired
    private ScanComponent scanComponent;

    @Autowired
    private AproximationBeanFactory aproximationBeanFactory;

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
    public AproximationBean aproximate(double latitude, double longitude) {
        return aproximate(latitude, longitude, Optional.empty(), Optional.empty());
    }

    /**
     * @param latitude
     * @param longitude
     * @param angle
     * @return
     */
    public AproximationBean aproximateDebug(double latitude, double longitude, int angle, Mode mode) {
        return aproximate(latitude, longitude, Optional.of(angle), Optional.of(mode));
    }

    /**
     * @param latitude
     * @param longitude
     * @param angle
     * @return
     */
    public AproximationBean aproximate(double latitude, double longitude, Optional<Integer> angle, Optional<Mode> mode) {
        try {
            return doAproximate(latitude, longitude, angle, mode);
        } catch (Exception e) {
            logger.error("Exception while invoking Rain Aproximation Rest Service.", e);
            return aproximationBeanFactory.createFriendlyErrorBean();
        }
    }

    /**
     * @param latitude
     * @param longitude
     * @return
     */
    public AproximationBean doAproximate(double latitude, double longitude, Optional<Integer> angle, Optional<Mode> mode) {

        Optional<Scan> scan = scanComponent.getScan();

        if (!scan.isPresent()) {
            logger.warn("Missing scans.");
            return aproximationBeanFactory.createEmptyScanBean();
        }

        Integer aproximationAngle = angle
//                .orElseGet(() -> windGetter.getWindDirection(latitude, longitude)
                .orElse(configuration.getAlgorithm().getDefaultWind());

        Mode aproximationMode = mode
                .orElse(new Mode(configuration.getAlgorithm().getMode()));

        Aproximation aproximation = rainAproximator.aproximate(scan.get(), latitude, longitude, aproximationAngle, aproximationMode);
        return aproximationBeanFactory.createBean(aproximation);
    }

}