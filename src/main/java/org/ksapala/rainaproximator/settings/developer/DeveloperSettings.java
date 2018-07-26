/**
 * 
 */
package org.ksapala.rainaproximator.settings.developer;

import org.ksapala.rainaproximator.settings.Property;
import org.ksapala.rainaproximator.settings.Settings;
import org.ksapala.rainaproximator.settings.Settings.InjectableSettings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author krzysztof
 *
 * contact: krzysztof.sapala@gmail.com
 *
 */
public class DeveloperSettings implements InjectableSettings {
	
	//private static SimpleDateFormat format = new SimpleDateFormat(Settings.get(Property.LAST_SCAN_DATE_FORMAT));
	
	/**
	 * 
	 */
	public DeveloperSettings() {
	}

	public Date getNow() {
//        Date now = parse("04/02/2015 14:00");
        return null;
	}
//
//	public static Date getLastScanDate() {
//		Date lastScanDate = parse("04/02/2015 13/49/00");
//        return lastScanDate;
//    }
	
	
//	private static Date parse(String dateString) {
//		try {
//	        Date date = DeveloperSettings.format.parse(dateString);
//	        return date;
//        } catch (ParseException e) {
//	        e.printStackTrace();
//        }
//		return null;
//	}

}
