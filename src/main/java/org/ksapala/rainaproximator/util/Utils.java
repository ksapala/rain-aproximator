/**
 * 
 */
package org.ksapala.rainaproximator.util;

import org.ksapala.rainaproximator.settings.Property;
import org.ksapala.rainaproximator.settings.Settings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public static String format(Date date) {
		if (date == null) {
			return "null";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(Settings.get(Property.LAST_SCAN_DATE_FORMAT));
		String formatted = dateFormat.format(date);
		return formatted;
	}
	
	public static Date parse(String stringDate) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Settings.get(Property.LAST_SCAN_DATE_FORMAT));
		Date date = dateFormat.parse(stringDate);
		return date;
	}

}
