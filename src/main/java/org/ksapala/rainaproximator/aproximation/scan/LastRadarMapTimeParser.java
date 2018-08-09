/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.scan;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LastRadarMapTimeParser {

    private final Logger logger = LoggerFactory.getLogger(LastRadarMapTimeParser.class);

	@Autowired
	private Configuration configuration;

	/**
	 * 
	 */
	public LastRadarMapTimeParser() {
	}

	/**
	 * @return
	 */
	public LocalDateTime parseLastRadarMapTime() throws IOException {
        String url = configuration.getScanner().getRadarMainPage();

        Document doc = Jsoup.connect(url).get();
        Elements scriptElements = doc.getElementsByTag("script");
        Element scriptElement = scriptElements.get(0);

        String javascript = scriptElement.data().replace("\n", "");
        int indexClock = javascript.lastIndexOf("//początek (1)");
        int indexDate = javascript.lastIndexOf("//data (0)");

        String clock = javascript.substring(indexClock - 8, indexClock - 3);
        String date = javascript.substring(indexDate - 13, indexDate - 3);

		LocalDateTime time = stringToDate(date + " " + clock);
		time = utcToLocal(time);

		logger.debug("Last radar map time: " + time);

		return time;
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
