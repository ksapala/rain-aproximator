/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.cloud;

import org.ksapala.rainaproximator.aproximation.image.RainImage;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.ScannedMap;
import org.ksapala.rainaproximator.aproximation.scan.converter.CoordinatesConverter;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageCalculator;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageIterator;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageOperator;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
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

    private final int IMAGE_WIDTH = RainImage.IMAGE_WIDTH;
    private final int IMAGE_HEIGHT = RainImage.IMAGE_HEIGHT;

    private final int IMAGE_MARGIN_TOP = RainImage.IMAGE_MARGIN_TOP;
    private final int IMAGE_MARGIN_BOTTOM = RainImage.IMAGE_MARGIN_BOTTOM;

    private Configuration.Algorithm.Cloud cloudConfiguration;

    /**
	 *
     * @param cloudConfiguration
     */
	public CloudLineBuilder(Configuration.Algorithm.Cloud cloudConfiguration) {
        this.cloudConfiguration = cloudConfiguration;
	}

    /**
     *
     * @param scan
     * @param x
     * @param y
     * @param alpha
     * @return
     */
    public List<CloudLine> buildCloudLines(@NotNull Scan scan, double x, double y, double alpha) {
        List<Point> imagePoints = getImagePoints(x, y, alpha);
        return scan.getMaps().stream()
                .map(map -> buildCloudLine(map, imagePoints))
                .collect(Collectors.toList());
    }

    /**
     * @param map
     * @param imagePoints
     * @return
     */
    private CloudLine buildCloudLine(ScannedMap map, List<Point> imagePoints) {
        CloudLine cloudLine = new CloudLine(cloudConfiguration, new boolean[imagePoints.size()], map.getTime());
        for (int i = 0; i < imagePoints.size(); i++) {
            Point point = imagePoints.get(i);
            int rgb = map.getImage().getRGB(point.x, point.y);
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
