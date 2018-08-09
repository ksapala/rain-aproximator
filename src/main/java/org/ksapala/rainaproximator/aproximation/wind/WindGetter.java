/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.wind;

import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * @author krzysztof
 * 
 * contact: krzysztof.sapala@gmail.com
 * 
 */
@Component
public class WindGetter {

    private final Logger logger = LoggerFactory.getLogger(WindGetter.class);

    @Autowired
	private Configuration configuration;

	/**
	 * 
	 */
	public WindGetter() {
	}

	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws AproximationException
	 */
	public Optional<Double> getWindDirection(double latitude, double longitude) {
		try {
			return doGetWindDirection(latitude, longitude);
		} catch (Exception e) {
			logger.error("Error while getting wind direction.", e);
			return Optional.empty();
		}
	}

	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	private Optional<Double> doGetWindDirection(double latitude, double longitude) {
		String urlWithParameters = MessageFormat.format(configuration.getWind().getUrl(), latitude, longitude);

        RestTemplate restTemplate = new RestTemplate();
        WeatherJson weatherJson = restTemplate.getForObject(urlWithParameters , WeatherJson.class);

        Optional<Double> windDirection = Optional.ofNullable(weatherJson.getWind().getDeg());
        if (windDirection.isPresent()) {
            logger.debug("Wind direction successfully get from http: " + windDirection);
        } else {
            logger.debug("Wind direction is null because there is no wind.");
        }
        return windDirection;
	}

}
