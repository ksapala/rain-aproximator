/**
 * 
 */
package org.ksapala.rainaproximator.rest;

import org.ksapala.rainaproximator.aproximation.AproximationResult;
import org.ksapala.rainaproximator.aproximation.RainAproximator;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.ScanComponent;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;
import org.ksapala.rainaproximator.rest.factory.AproximationResultBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;



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


    public RainAproximatorService() {
	}
	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public AproximationResultBean aproximate(double latitude, double longitude) {
		try {
			return doAproximate(latitude, longitude);
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
	public AproximationResultBean doAproximate(double latitude, double longitude) throws AproximationException {
        Scan scan = scanComponent.getScan();
        if (scan == null) {
            logger.warn("Missing scans.");
            return aproximationResultBeanFactory.createEmptyScanBean();
        }
        AproximationResult aproximationResult = rainAproximator.aproximate(scan, latitude, longitude);
		return aproximationResultBeanFactory.createBean(aproximationResult);
	}


}