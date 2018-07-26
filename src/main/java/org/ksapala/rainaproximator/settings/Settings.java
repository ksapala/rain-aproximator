package org.ksapala.rainaproximator.settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.settings.developer.DeveloperSettings;
import org.ksapala.rainaproximator.settings.developer.ProductionSettings;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;


/**
 * @author krzysztof
 *
 * contact: krzysztof.sapala@gmail.com
 *
 */
/**
 * @author krzysztof
 *
 * contact: krzysztof.sapala@gmail.com
 *
 */
public class Settings {
	
	private final static Logger LOGGER = LogManager.getLogger(Settings.class);
	
	private final static String DEVELOPER_MODE_PROPERTY = "mode.developer";
	private final static String DEVELOPER_MODE_PROPERTY_TRUE = "1";

	private final static String SPLIT_CHAR = ":";
	public final static String DEFAULT_PROPERTIES_FILE = "properties/config.properties";
	public final static String DEFAULT_TEST_PROPERTIES_FILE = "properties/config_test.properties";
	
	public final static String DATA_DIR_VARIABLE_NAME = "dataDir";
	public final static String DATA_DIR_EXPRESSION = "${DATA_DIR}";
	
	private static final long TWO_HOURS_MILISECONDS = 1000 * 60 * 60 * 2;
	
	public static InjectableSettings injectableSettings;

	private static Properties properties;
	
	static {
		setProperDevelopmentMode();
	}

	private static void setProperDevelopmentMode() {
	    String property = System.getProperty(DEVELOPER_MODE_PROPERTY);
		if (property!= null && property.equals(DEVELOPER_MODE_PROPERTY_TRUE)) {
			LOGGER.info("Running in developer mode.");
			Settings.injectableSettings = new DeveloperSettings();
		} else {
			LOGGER.info("Running in production mode.");
			Settings.injectableSettings = new ProductionSettings();
		}
    }

	/**
	 * For JUnit.
	 */
	public static void initDefaultSettings() {
		//initSettings(DEFAULT_PROPERTIES_FILE);
    }

	public Settings() {
	}
	
//	public static String get(String property) {
//		return getStringProperty(property);
//	}
	
	/**
	 * @param property
	 * @return
	 */
//	public static int getInt(String property) {
//		String stringProperty = getStringProperty(property);
//		int intValue = Integer.parseInt(stringProperty);
//		return intValue;
//	}
	
	/**
	 * @param property
	 * @return
	 */
//	public static long getLong(String property) {
//		String stringProperty = getStringProperty(property);
//		long longValue = Long.parseLong(stringProperty);
//		return longValue;
//	}
	
	/**
	 * @param property
	 * @return
	 */
//	public static String[] getArray(String property) {
//		String stringProperty = getStringProperty(property);
//		String[] split = stringProperty.split(SPLIT_CHAR);
//		return split;
//	}
	

	/**
	 * @param property
	 * @return
	 */
//	public static boolean getBoolean(String property) {
//		String stringProperty = getStringProperty(property);
//		boolean result = Boolean.valueOf(stringProperty);
//		return result;
//	}
	
	/**
	 * @param property
	 * @return
	 */
//	private static String getStringProperty(String property) {
//		if (Settings.properties == null) {
//			throw new NullPointerException("Properties are null. Perhaps was not initialized properly.");
//		}
//		String stringProperty = Settings.properties.getProperty(property);
//		if (stringProperty == null) {
//			String message = MessageFormat.format("Property {0} not set.", property);
//			throw new NullPointerException(message);
//		}
//		return stringProperty;
//	}
	
//	public static void initSettings(String propertiesFilePath) {
//		PropertiesReader propertiesReader = new PropertiesReader(propertiesFilePath);
//		Settings.properties = propertiesReader.readFromFile();
//	}


	public static Date getNow() {
		return Settings.injectableSettings.getNow();
	}

	public interface InjectableSettings {
		public Date getNow();
	}

//	public static boolean isDeveloperMode() {
//	    return Settings.injectableSettings instanceof DeveloperSettings;
//    }

//	public static Date getVitalityDate() {
//		Date twoHoursAgo = new Date(Settings.getNow().getTime() - TWO_HOURS_MILISECONDS);
//		return twoHoursAgo;
//	}

}
