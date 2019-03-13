package org.ksapala.rainaproximator.aproximation.domainfilters;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.regression.RegressionPoint;
import org.ksapala.rainaproximator.aproximation.structure.RegressionPointStructure;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Filters {

    private final double EXPAND_FACTOR = 1.5;

    public Filters() {
    }

    public List<CloudLine> filterRainCandidates(List<CloudLine> cloudLines) {
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
    public List<CloudLine> filterSunCandidates(List<CloudLine> cloudLines) {
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

    /**
     * @param structures
     * @return
     */
    public List<RegressionPointStructure> filterGoodFit(List<RegressionPointStructure> structures) {
        List<RegressionPointStructure> result = new ArrayList<>();
        if (structures.isEmpty()) {
            return result;
        }

        List<RegressionPoint> regressionPoints = structures.stream()
                .map(RegressionPointStructure::getRegressionPoint)
                .collect(Collectors.toList());

        RegressionPointStructure lastStructure = structures.get(structures.size() - 1);
        List<Integer> differences = getDifferences(regressionPoints);

        int lastValue = lastStructure.getRegressionPoint().getDistance().getValue();
        double median = Math.abs(getMedian(differences)); // minus for reverse iteration
        double expand = median * EXPAND_FACTOR;
        double borderDown = lastValue - median;
        double borderUp = lastValue + median;

        for (int i = structures.size() - 1; i >= 0 ; i--) {
            RegressionPointStructure structure = structures.get(i);
            int value = structure.getRegressionPoint().getDistance().getValue();

            if (value > borderDown - expand && value < borderUp + expand) {
                result.add(structure);
                borderDown = value - median;
                borderUp = value + median;
            } else {
                borderDown = borderDown - median;
                borderUp = borderUp + median;
            }
        }
        Collections.reverse(result);
        return result;
    }

    /**
     * @param regressionPoints
     * @return
     */
    private List<Integer> getDifferences(List<RegressionPoint> regressionPoints) {
        return IntStream.range(0, regressionPoints.size() - 1)
                .mapToObj(i -> regressionPoints.get(i + 1).getDistance().getValue()
                        - regressionPoints.get(i).getDistance().getValue())
                .collect(Collectors.toList());
    }

    private double getMedian(List<Integer> numbers) {
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        numbers.forEach(statistics::addValue);
        return statistics.getPercentile(50);
    }

}
