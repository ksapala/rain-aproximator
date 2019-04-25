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

    public static long dateToMillis(LocalDateTime time) {
        return time.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static LocalDateTime millisToDate(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
    }

    public static String timeToString(LocalDateTime time, Configuration configuration) {
        return timeToString(time, configuration.getUserTimeFormat());
    }

    public static String timeToString(LocalDateTime time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return time.format(formatter);
    }

}
