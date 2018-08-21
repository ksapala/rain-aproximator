/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.cloud;

import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.ScannedMap;
import org.ksapala.rainaproximator.aproximation.scan.converter.CoordinatesConverter;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageCalculator;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageIterator;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageOperator;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private final Logger logger = LoggerFactory.getLogger(CloudLineBuilder.class);

    private final int IMAGE_WIDTH = CoordinatesConverter.IMAGE_WIDTH;
    private final int IMAGE_HEIGHT = CoordinatesConverter.IMAGE_HEIGHT;

    private final int IMAGE_MARGIN_TOP = CoordinatesConverter.IMAGE_MARGIN_TOP;
    private final int IMAGE_MARGIN_BOTTOM = CoordinatesConverter.IMAGE_MARGIN_BOTTOM;

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
		logCloudLines(logger,"Created cloud lines bellow:", cloudLines); //$NON-NLS-1$
		return cloudLines;
	}

	/**
	 * @param cloudLines
	 */
	public static void logCloudLines(Logger logger, String info, List<CloudLine> cloudLines) {
		logger.debug(info);
		for (CloudLine cloudLine : cloudLines) {
			logger.debug(cloudLine.toString());
        }
    }

    /**
     * @param x
     * @param y
     * @param alpha
     * @return
     */
    private List<CloudLine> parseToCloudLines(Scan scan, double x, double y, double alpha) {
        List<Point> imagePoints = getImagePoints(x, y, alpha);
        return scan.getMaps().stream()
                .map(map -> parseToCloudLine(map, imagePoints))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param scannedMap
     * @param imagePoints
     * @return
     */
    private CloudLine parseToCloudLine(ScannedMap scannedMap, List<Point> imagePoints) {
        logger.debug("Trying to parse radar map file:" + scannedMap.getImage());
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
        boolean containsInHeight = y >= 0 + IMAGE_MARGIN_TOP && y < IMAGE_HEIGHT - IMAGE_MARGIN_BOTTOM;
        return containsInWidth && containsInHeight;
    }




}
