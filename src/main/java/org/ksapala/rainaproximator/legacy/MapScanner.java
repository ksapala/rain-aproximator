/**
 * 
 */
package org.ksapala.rainaproximator.legacy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author krzysztof
 *
 */
public class MapScanner {

	private final static Logger LOGGER = LogManager.getLogger(MapScanner.class);
	private static final String SERVER_PROPERTIES_FILE_PARAMETER = "serverPropertiesFile";
	
	/**
	 * 
	 */
	public MapScanner() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String propertiesFile = getServerPropertiesFile();
//		RainAproximatorFacade facade = new RainAproximatorFacade(propertiesFile);
//		try {
//			facade.scanMapsOnly();
//		} catch (AproximationException e) {
//			LOGGER.error(e);
//		}
	}
	

	
	/**
	 * @return
	 */
	private static String getServerPropertiesFile() {
		String serverPropertiesFile = System.getProperty(SERVER_PROPERTIES_FILE_PARAMETER);
		return serverPropertiesFile;
	}

}
