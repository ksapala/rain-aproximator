package org.ksapala.rainaproximator.withmain;

import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.settings.Property;
import org.ksapala.rainaproximator.settings.Settings;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class ImageParserTest {

	private static final String WINDTEST_FILE_WIND = "31";

	
	public ImageParserTest() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException  {
//		Settings.initDefaultSettings();
//
//		ImageParser imageParser = new ImageParser();
//		double x = 400;
//		double y = 400;
//		double alpha = 134;
//
//		File file = new File(getFullRadarMapTestFilePath(WINDTEST_FILE_WIND));
//
//		CloudLine cloudLine = imageParser.parseToCloudLine(file, x, y, alpha);
//		cloudLine.print();
		
	}
	

	private static String getFullRadarMapTestFilePath(String radarImageIdentifier) {
//		String radarMapFileName = MessageFormat.format(Settings.get(Property.RADAR_MAP_FILE_NAME), radarImageIdentifier);
//		String fullFilePath = Settings.get(Property.RADAR_MAP_FILE_PATH_TEST) + radarMapFileName;
//		return fullFilePath;
        return null;
	}


}
