/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.cloud;

import com.sun.scenario.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.aproximation.map.Scan;
import org.ksapala.rainaproximator.aproximation.map.converter.CoordinatesConverter;
import org.ksapala.rainaproximator.aproximation.map.imageoperator.ImageCalculator;
import org.ksapala.rainaproximator.aproximation.map.imageoperator.ImageIterator;
import org.ksapala.rainaproximator.aproximation.map.imageoperator.ImageOperator;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.aproximation.map.LastRadarMapDateParser;
import org.ksapala.rainaproximator.settings.Property;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author krzysztof
 *
 * contact: krzysztof.sapala@gmail.com
 *
 */
public class CloudLineBuilder {

	private final static Logger LOGGER = LogManager.getLogger(CloudLineBuilder.class);

    private int IMAGE_WIDTH = CoordinatesConverter.IMAGE_WIDTH;
    private int IMAGE_HEIGHT = CoordinatesConverter.IMAGE_HEIGHT;

	private LastRadarMapDateParser lastRadarMapDateParser;

	/**
	 *
	 */
	public CloudLineBuilder() {
		this.lastRadarMapDateParser = new LastRadarMapDateParser();
	}

	/**
	 * @param x
	 * @param y
	 * @param alpha
	 * @return
	 * @throws AproximationException 
	 */
	public List<CloudLine> createCloudLines(Scan scan, double x, double y, double alpha) throws AproximationException {
		List<CloudLine> cloudLines = parseToCloudLines(scan, x, y, alpha);
		fillDates(cloudLines);
		logCloudLines("Created cloud lines bellow:", cloudLines); //$NON-NLS-1$
		return cloudLines;
	}

	/**
	 * @param cloudLines
	 */
	public static void logCloudLines(String info, List<CloudLine> cloudLines) {
		LOGGER.info(info);
		for (CloudLine cloudLine : cloudLines) {
			LOGGER.info(cloudLine.toString());
        }
    }

//	/**
//	 * @param cloudLines
//	 * @throws AproximationException
//	 */
//	public void fillDates(List<CloudLine> cloudLines) throws AproximationException {
//	    Date lastScanDateFromFile = this.lastRadarMapDateParser.readLastScanDateFromFile();
//		if (lastScanDateFromFile == null) {
//			throw new NullPointerException("lastScanDateFromFile is null."); //$NON-NLS-1$
//		}
//
//		checkLastScanDateVitality(lastScanDateFromFile);
//
//		Date date = lastScanDateFromFile;
//		long lastScanDateIntervalMiliseconds = Settings.getLong(Property.LAST_SCAN_DATE_INTERVAL_MILISECONDS);
//
//		for (int i = cloudLines.size() - 1; i >= 0; i--) {
//	        CloudLine cloudLine = cloudLines.get(i);
//	        cloudLine.setDate(date);
//	        date = new Date(date.getTime() - lastScanDateIntervalMiliseconds);
//        }
//    }


	public void setLastRadarMapDateParser(LastRadarMapDateParser lastRadarMapDateParser) {
		this.lastRadarMapDateParser = lastRadarMapDateParser;
	}

    /**
     * @param x
     * @param y
     * @param alpha
     * @return
     */
    public List<CloudLine> parseToCloudLines(Scan scan, double x, double y, double alpha) {
        return scan.getMaps().stream()
                .map(map -> parseToCloudLine(map, x, y, alpha))
                .collect(Collectors.toList());
    }

    /**
     * @param radarMap
     * @param x
     * @param y
     * @param alpha
     * @return
     */
    public CloudLine parseToCloudLine(BufferedImage radarMap, double x, double y, double alpha) {
        LOGGER.debug("Trying to parse radar map file:" + radarMap);
        List<Point> imagePoints = getImagePoints(x, y, alpha);
        CloudLine cloudLine = getCloudLine(radarMap, imagePoints);
        return cloudLine;
    }

    /**
     * @param mapImage
     * @param imagePoints
     * @return
     */
    private CloudLine getCloudLine(BufferedImage mapImage, List<Point> imagePoints) {
        CloudLine cloudLine = new CloudLine(imagePoints.size());
        for (int i = 0; i < imagePoints.size(); i++) {
            Point point = imagePoints.get(i);
            int rgb = mapImage.getRGB(point.x, point.y);
            cloudLine.addRgb(rgb, i);
        }
        return cloudLine;
    }

    /**
     * @param startX
     * @param startY
     * @param alpha
     * @return
     */
    private List<Point> getImagePoints(double startX, double startY, double alpha) {
        List<Point> points = new ArrayList<Point>();

        ImageOperator imageOperator = getImageOperator(alpha);
        ImageCalculator imageCalculator = imageOperator.getImageCalculator();
        ImageIterator imageIterator = imageOperator.getImageIterator();

        while (imageIterator.hasNext()) {
            int index = imageIterator.next();
            double x = imageCalculator.getX(index);
            double y = imageCalculator.getY(index);
            int pointX = (int) (startX + x);
            int pointY = (int) (startY - y);
            Point point = new Point(pointX, pointY);
            boolean imageContains = imageContains(point);
            if (!imageContains) {
                break;
            }
            points.add(point);
        }
        return points;
    }

    private ImageOperator getImageOperator(double alpha) {
        return new ImageOperator(alpha, Settings.getInt(Property.IMAGE_SAMPLES_COUNT));
    }

    private boolean imageContains(Point point) {
        double x = point.getX();
        double y = point.getY();
        boolean containsInWidth = x >= 0 && x < IMAGE_WIDTH;
        boolean containsInHeight = y >= 0 && y < IMAGE_HEIGHT;
        return containsInWidth && containsInHeight;
    }


}
