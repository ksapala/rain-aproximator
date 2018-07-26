/**
 * 
 */
package org.ksapala.rainaproximator.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.aproximation.AproximationResult;
import org.ksapala.rainaproximator.aproximation.RainAproximator;
import org.ksapala.rainaproximator.aproximation.map.Scan;
import org.ksapala.rainaproximator.aproximation.map.ScanKeeper;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;
import org.ksapala.rainaproximator.rest.factory.AproximationResultBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;



/**
 * @author krzysztof
 *
 */
@Component
public class RainAproximatorService {
	
	private final static Logger LOGGER = LogManager.getLogger(RainAproximatorService.class);

    @Autowired
    private Configuration configuration;

    @Autowired
    private ScanKeeper scanKeeper;


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
			LOGGER.error("Exception while invoking Rain Aproximation Rest Service.", e);
			return AproximationResultBeanFactory.FRIENDLY_EXCEPTION_RESULT;
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
		RainAproximator rainAproximator = new RainAproximator(configuration);
        Scan scan = scanKeeper.getScan();
        AproximationResult aproximationResult = rainAproximator.aproximate(scan, latitude, longitude);

		AproximationResultBean bean = AproximationResultBeanFactory.createBean(aproximationResult);
		return bean;
	}


}