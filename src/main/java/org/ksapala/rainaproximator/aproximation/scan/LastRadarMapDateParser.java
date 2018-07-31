/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.scan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
public class LastRadarMapDateParser {

	private final static Logger LOGGER = LogManager.getLogger(LastRadarMapDateParser.class);

	@Autowired
	private Configuration configuration;

	/**
	 * 
	 */
	public LastRadarMapDateParser() {
	}


	/**
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public LocalDateTime parseLastRadarMapDate() throws IOException {
		Document doc = Jsoup.connect(configuration.getScanner().getRadarMainPage()).get();
		Element tdWithLastRadarMapDateElement = doc.getElementById(configuration.getScanner().getLastRadarMapDateElementId());
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
		ZonedDateTime utcDate = ZonedDateTime.of(date, ZoneOffset.UTC); // map is in UTC zone
		ZonedDateTime converted = utcDate.withZoneSameInstant(ZoneId.of(configuration.getScanner().getLastRadarMapDateZone()));
		date = converted.toLocalDateTime();
		return date;
	}

	/**
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	private LocalDateTime stringToDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(configuration.getScanner().getLastRadarMapDateFormat());
		LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
	    return dateTime;
    }

}
