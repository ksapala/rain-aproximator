package org.ksapala.rainaproximator.settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {


	private final static Logger LOGGER = LogManager.getLogger(PropertiesReader.class);
	
	private String dataDir;
	private String propertiesFilePath;

	public PropertiesReader(String propertiesFilePath) {
		this.propertiesFilePath = propertiesFilePath;
		initDataDir();
	}

	/**
	 * 
	 */
	private void initDataDir() {
	    String dataDir = System.getProperty(Settings.DATA_DIR_VARIABLE_NAME);
		if (dataDir != null) {
			this.dataDir = dataDir;
			LOGGER.info("Initializing config.properties with dataDir: " + this.dataDir);
		} else {
			throw new RuntimeException("Cannot read properties file. " + Settings.DATA_DIR_VARIABLE_NAME 
					+ " jvm variable not defined. Please add variable -D prefix.");
		}
    }
	
	public Properties readFromFile() {
		Properties properties = new Properties();
		InputStream input = null;
	 
		try {
			input = new FileInputStream(this.propertiesFilePath);
			properties.load(input);
			
			repalceDataDir(properties);
			
			return properties;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param properties
	 */
	private void repalceDataDir(Properties properties) {
	    for (Object key : properties.keySet()) {
	    	String keyString = (String) key;
	    	String value = (String) properties.get(keyString);
	    	String replaced = replaceDataDir(value);
	    	properties.setProperty(keyString, replaced);
        }
    }

	/**
	 * @param value
	 * @return
	 */
	private String replaceDataDir(String value) {
	    if (value.contains(Settings.DATA_DIR_EXPRESSION)) {
	    	value = value.replace(Settings.DATA_DIR_EXPRESSION, this.dataDir);
	    }
	    return value;
    }

}
