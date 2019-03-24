package org.ksapala.rainaproximator.aproximation.domainfilters;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.aproximation.regression.RegressionPoint;
import org.ksapala.rainaproximator.aproximation.structure.RegressionPointStructure;
import org.ksapala.rainaproximator.configuration.Configuration;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Filters {

    private Configuration.Algorithm algorithm;

    public Filters(Configuration.Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public List<Cloud> filterRainCandidates(List<Cloud> clouds) {
        List<Cloud> result = new ArrayList<>(clouds.size());
        boolean changedToRain = false;
        for (ListIterator<Cloud> iterator = clouds.listIterator(clouds.size()); iterator.hasPrevious(); ) {
            Cloud cloud = iterator.previous();
            if (cloud.isFutureRainDistanceInfinity()) {
                break;
            }
            if (changedToRain) { // second part - rain
                if (cloud.isRain()) {
                    result.add(cloud);
                } else {
                    break;
                }
            } else { // first part - sun
                result.add(cloud);
                if (cloud.isRain()) {
                    changedToRain = true;
                }
            }
        }
        Collections.reverse(result);
        return result;
    }

    /**
     *
     * @param clouds
     * @return
     */
    public List<Cloud> filterSunCandidates(List<Cloud> clouds) {
        List<Cloud> result = new ArrayList<>(clouds.size());
        boolean changedToSun = false;
        for (ListIterator<Cloud> iterator = clouds.listIterator(clouds.size()); iterator.hasPrevious(); ) {
            Cloud cloud = iterator.previous();
            if (cloud.isFutureSunDistanceInfinity()) {
                break;
            }
            if (changedToSun) { // second part - sun
                if (cloud.isSun()) {
                    result.add(cloud);
                } else {
                    break;
                }
            } else { // first part - rain
                result.add(cloud);
                if (cloud.isSun()) {
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
        double median = Math.abs(getMedian(differences));
        double expand = median * algorithm.getGoodFitExpandFactor();
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
