/**
 * 
 */
package org.ksapala.rainaproximator.rest.service;

import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.json.WeatherJson;
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
public class WindService {

    private final Logger logger = LoggerFactory.getLogger(WindService.class);

    @Autowired
    private Configuration configuration;

    /**
	 * 
	 */
    public WindService() {
    }

	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws AproximationException
	 */
	public Optional<Integer> getWindDirection(double latitude, double longitude) {
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
	private Optional<Integer> doGetWindDirection(double latitude, double longitude) {
		String urlWithParameters = MessageFormat.format(configuration.getWind().getUrl(), latitude, longitude);

        RestTemplate restTemplate = new RestTemplate();
        WeatherJson weatherJson = restTemplate.getForObject(urlWithParameters , WeatherJson.class);

        Optional<Double> windDirection = Optional.ofNullable(weatherJson.getWind().getDeg());

        if (windDirection.isPresent()) {
            logger.debug("Wind direction successfully get from http: " + windDirection);
            return Optional.of(Math.toIntExact(Math.round(windDirection.get())));
        } else {
            logger.debug("Wind direction is null because there is no wind.");
            return Optional.empty();
        }
	}

}
