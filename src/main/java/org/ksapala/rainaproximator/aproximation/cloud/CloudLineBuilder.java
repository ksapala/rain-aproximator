/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.cloud;

import com.sun.scenario.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.aproximation.scan.LastRadarMapDateParser;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.ScannedMap;
import org.ksapala.rainaproximator.aproximation.scan.converter.CoordinatesConverter;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageCalculator;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageIterator;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageOperator;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.settings.Property;

import java.awt.*;
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

    private Configuration.Algorithm.Cloud cloudConfiguration;

    /**
	 *
     * @param cloudConfiguration
     */
	public CloudLineBuilder(Configuration.Algorithm.Cloud cloudConfiguration) {
        this.cloudConfiguration = cloudConfiguration;
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
     * @param scannedMap
     * @param x
     * @param y
     * @param alpha
     * @return
     */
    public CloudLine parseToCloudLine(ScannedMap scannedMap, double x, double y, double alpha) {
        LOGGER.debug("Trying to parse radar map file:" + scannedMap.getImage());
        List<Point> imagePoints = getImagePoints(x, y, alpha);
        CloudLine cloudLine = buildCloudLine(scannedMap, imagePoints);
        return cloudLine;
    }

    /**
     * @param scannedMap
     * @param imagePoints
     * @return
     */
    private CloudLine buildCloudLine(ScannedMap scannedMap, List<Point> imagePoints) {
        CloudLine cloudLine = new CloudLine(cloudConfiguration, new boolean[imagePoints.size()], scannedMap.getTime());
        for (int i = 0; i < imagePoints.size(); i++) {
            Point point = imagePoints.get(i);
            int rgb = scannedMap.getImage().getRGB(point.x, point.y);
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
        return new ImageOperator(alpha, cloudConfiguration.getCloudLineLength());
    }

    private boolean imageContains(Point point) {
        double x = point.getX();
        double y = point.getY();
        boolean containsInWidth = x >= 0 && x < IMAGE_WIDTH;
        boolean containsInHeight = y >= 0 && y < IMAGE_HEIGHT;
        return containsInWidth && containsInHeight;
    }


}
