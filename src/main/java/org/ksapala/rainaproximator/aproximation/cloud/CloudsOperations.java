package org.ksapala.rainaproximator.aproximation.cloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class CloudsOperations {

    public CloudsOperations() {
    }

    /**
     *
     * @param cloudLines
     * @return
     */
    public static List<CloudLine> filterForRainRegression(List<CloudLine> cloudLines) {
        List<CloudLine> result = new ArrayList<>(cloudLines.size());
        boolean changedToRain = false;
        for (ListIterator<CloudLine> iterator = cloudLines.listIterator(cloudLines.size()); iterator.hasPrevious(); ) {
            CloudLine cloudLine = iterator.previous();
            if (cloudLine.isFutureRainDistanceInfinity()) {
                break;
            }
            if (changedToRain) { // second part - rain
                if (cloudLine.isRain()) {
                    result.add(cloudLine);
                } else {
                    break;
                }
            } else { // first part - sun
                result.add(cloudLine);
                if (cloudLine.isRain()) {
                    changedToRain = true;
                }
            }
        }
        Collections.reverse(result);
        return result;
    }

    /**
     *
     * @param cloudLines
     * @return
     */
    public static List<CloudLine> filterForSunRegression(List<CloudLine> cloudLines) {
        List<CloudLine> result = new ArrayList<>(cloudLines.size());
        boolean changedToSun = false;
        for (ListIterator<CloudLine> iterator = cloudLines.listIterator(cloudLines.size()); iterator.hasPrevious(); ) {
            CloudLine cloudLine = iterator.previous();
            if (cloudLine.isFutureSunDistanceInfinity()) {
                break;
            }
            if (changedToSun) { // second part - sun
                if (cloudLine.isSun()) {
                    result.add(cloudLine);
                } else {
                    break;
                }
            } else { // first part - rain
                result.add(cloudLine);
                if (cloudLine.isSun()) {
                    changedToSun = true;
                }
            }
        }
        Collections.reverse(result);
        return result;
    }

    public static boolean isRain(List<CloudLine> cloudLines) {
        if (cloudLines.isEmpty()) {
            throw new RuntimeException("Cloud lines list cannot be empty.");
        }
        CloudLine last = cloudLines.get(cloudLines.size() - 1);
        return last.isRain();
    }

    public static boolean isSun(List<CloudLine> cloudLines) {
        return !isRain(cloudLines);
    }
}
