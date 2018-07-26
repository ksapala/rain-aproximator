/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.wind;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;

/**
 * @author krzysztof
 * 
 * contact: krzysztof.sapala@gmail.com
 * 
 */
public class WindGetter {

	private final static Logger LOGGER = LogManager.getLogger(WindGetter.class);
	
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
	 *
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws AproximationException
	 */
	public double getWindDirection(double latitude, double longitude) throws AproximationException {
		int windRetries = windConfiguration.getRetries();
		return getWindDirection(latitude, longitude, windRetries);
	}

	/**
	 * @param latitude
	 * @param longitude
	 * @param windRetries
	 * @return
	 * @throws AproximationException
	 */
	private double getWindDirection(double latitude, double longitude, int windRetries) throws AproximationException {
		try {
			return doGetWindDirection(latitude, longitude);
		} catch (IOException e) {
			if (windRetries > 1) {
				LOGGER.warn(e + ". Exception occured while getting wind direction from http. Retrying...");
				return getWindDirection(latitude, longitude,  windRetries - 1);
			}
			throw new AproximationException("Error while getting wind direction.", e);
		}
	}

	/**
	 * @param latitude
	 * @param longitude
	 * @return
	 * @throws IOException
	 */
	public double doGetWindDirection(double latitude, double longitude) throws IOException {
		String urlWithParameters = MessageFormat.format(windConfiguration.getUrl(), latitude, longitude);
		URL url = new URL(urlWithParameters);
		try (InputStream inputStream = url.openStream(); JsonReader jsonReader = Json.createReader(inputStream)) {
			
			JsonObject jsonObject = jsonReader.readObject();
			JsonObject jsonObjectWind = jsonObject.getJsonObject(WIND);
			JsonNumber jsonNumberDeg = jsonObjectWind.getJsonNumber(DEG);
			double windDirection = jsonNumberDeg.doubleValue();
			LOGGER.info("Wind direction from http: " + windDirection);
			return windDirection;
		}
	}

}
