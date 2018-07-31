/**
 * 
 */
package org.ksapala.rainaproximator.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author krzysztof
 *
 */
public class Utils {

	/**
	 * 
	 */
	public Utils() {
	}

    public static long localDateAndTimeToMillis(LocalDateTime time) {
        return time.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static LocalDateTime millisToLocalDateAndTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
    }

//	public static String format(Date date) {
//		if (date == null) {
//			return "null";
//		}
//		SimpleDateFormat dateFormat = new SimpleDateFormat(Settings.get(Property.LAST_SCAN_DATE_FORMAT));
//		String formatted = dateFormat.format(date);
//		return formatted;
//	}
//
//	public static Date parse(String stringDate) throws ParseException {
//		SimpleDateFormat dateFormat = new SimpleDateFormat(Settings.get(Property.LAST_SCAN_DATE_FORMAT));
//		Date date = dateFormat.parse(stringDate);
//		return date;
//	}

}
