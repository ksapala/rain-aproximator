/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.wind;

import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * @author krzysztof
 * 
 * contact: krzysztof.sapala@gmail.com
 * 
 */
public class WindGetter {

    private final Logger logger = LoggerFactory.getLogger(WindGetter.class);

	private static final String DEG = "deg";
	private static final String WIND = "wind";

	private final Configuration.Wind windConfiguration;

	/**
	 * 
	 */
	public WindGetter(Configuration.Wind windConfiguration) {
		this.windConfiguration = windConfiguration;
	}

	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws AproximationException
	 */
	public Optional<Double> getWindDirection(double latitude, double longitude) throws AproximationException {
		try {
			return doGetWindDirection(latitude, longitude);
		} catch (Exception e) {
			throw new AproximationException("Error while getting wind direction.", e);
		}
	}

	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	private Optional<Double> doGetWindDirection(double latitude, double longitude) {
		String urlWithParameters = MessageFormat.format(windConfiguration.getUrl(), latitude, longitude);

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
