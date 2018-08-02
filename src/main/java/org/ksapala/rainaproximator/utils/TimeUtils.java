/**
 * 
 */
package org.ksapala.rainaproximator.utils;

import org.ksapala.rainaproximator.configuration.Configuration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author krzysztof
 *
 */
public class TimeUtils {

	/**
	 * 
	 */
	public TimeUtils() {
	}

    public static long localDateAndTimeToMillis(LocalDateTime time) {
        return time.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static LocalDateTime millisToLocalDateAndTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
    }

    public static DateTimeFormatter getFormatter(Configuration configuration) {
	    return DateTimeFormatter.ofPattern(configuration.getUserTimeFormat());
    }

}
