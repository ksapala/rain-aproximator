/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author krzysztof
 *
 * contact: krzysztof.sapala@gmail.com
 *
 */
public class LastRadarMapDateParser {

	private final static Logger LOGGER = LogManager.getLogger(LastRadarMapDateParser.class);
	
	private Configuration.Scanner scannerConfiguration;

	/**
	 * 
	 */
	public LastRadarMapDateParser(Configuration.Scanner scannerConfiguration) {
		this.scannerConfiguration = scannerConfiguration;
	}


	/**
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public LocalDateTime parseLastRadarMapDate() throws IOException {
		Document doc = Jsoup.connect(scannerConfiguration.getRadarMainPage()).get();
		Element tdWithLastRadarMapDateElement = doc.getElementById(scannerConfiguration.getLastRadarMapDateElementId());
		String dateString = tdWithLastRadarMapDateElement.text();

		LocalDateTime date = stringToDate(dateString);
		date = utcToLocal(date);

		LOGGER.debug("Last radar map date: " + date);

		return date;
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	private LocalDateTime utcToLocal(LocalDateTime date) {
		ZonedDateTime utcDate = ZonedDateTime.of(date, ZoneOffset.UTC);
		ZonedDateTime converted = utcDate.withZoneSameInstant(ZoneId.of(scannerConfiguration.getLastRadarMapDateZone()));
		date = converted.toLocalDateTime();
		return date;
	}

	/**
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	private LocalDateTime stringToDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(scannerConfiguration.getLastRadarMapDateFormat());
		LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
	    return dateTime;
    }

}
