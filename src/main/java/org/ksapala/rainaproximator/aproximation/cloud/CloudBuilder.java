package org.ksapala.rainaproximator.aproximation.cloud;

import org.ksapala.rainaproximator.aproximation.image.RainImage;
import org.ksapala.rainaproximator.aproximation.scan.Scan;
import org.ksapala.rainaproximator.aproximation.scan.ScannedMap;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageCalculator;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageIterator;
import org.ksapala.rainaproximator.aproximation.scan.imageoperator.ImageOperator;
import org.ksapala.rainaproximator.configuration.Configuration;

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
public class CloudBuilder {

    private Configuration.Algorithm.Cloud cloudConfiguration;

    /**
	 *
     * @param cloudConfiguration
     */
	public CloudBuilder(Configuration.Algorithm.Cloud cloudConfiguration) {
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
    public List<Cloud> buildClouds(@NotNull Scan scan, double x, double y, double alpha) {
        List<Point> imagePoints = getImagePoints(x, y, alpha);
        return scan.getMaps().stream()
                .map(map -> buildCloud(map, imagePoints))
                .collect(Collectors.toList());
    }

    /**
     * @param map
     * @param imagePoints
     * @return
     */
    private Cloud buildCloud(ScannedMap map, List<Point> imagePoints) {
        Cloud cloud = new Cloud(cloudConfiguration, new boolean[imagePoints.size()], map.getTime());
        for (int i = 0; i < imagePoints.size(); i++) {
            Point point = imagePoints.get(i);
            int rgb = map.getImage().getRGB(point.x, point.y);
            cloud.addRgb(rgb, i);
        }
        return cloud;
    }

    /**
     * @param startX
     * @param startY
     * @param alpha
     * @return
     */
    private List<Point> getImagePoints(double startX, double startY, double alpha) {
        List<Point> points = new ArrayList<>();

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
        return new ImageOperator(alpha, cloudConfiguration.getCloudLength());
    }

    private boolean imageContains(Point point) {
        double x = point.getX();
        double y = point.getY();
        boolean containsInWidth = x >= 0 && x < RainImage.IMAGE_WIDTH;
        boolean containsInHeight = y >= RainImage.IMAGE_MARGIN_TOP && y < RainImage.IMAGE_HEIGHT - RainImage.IMAGE_MARGIN_BOTTOM;
        return containsInWidth && containsInHeight;
    }
}
