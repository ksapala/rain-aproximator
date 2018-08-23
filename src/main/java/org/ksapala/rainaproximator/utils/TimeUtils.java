/**
 * 
 */
package org.ksapala.rainaproximator.utils;

import org.apache.tomcat.jni.Local;
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

    public static String timeToString(LocalDateTime time, Configuration configuration) {
        DateTimeFormatter formatter = getFormatter(configuration);
        return time.format(formatter);
    }

    public static LocalDateTime parseInTest(String timeString, Configuration configuration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(configuration.getTestTimeFormat());
        return LocalDateTime.parse(timeString, formatter);
    }

}
