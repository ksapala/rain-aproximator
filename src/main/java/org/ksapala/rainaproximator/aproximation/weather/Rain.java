package org.ksapala.rainaproximator.aproximation.weather;

import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.aproximation.regression.RegressionPoint;
import org.ksapala.rainaproximator.utils.TimeFactory;
import org.ksapala.rainaproximator.configuration.Configuration;

import java.util.List;

/**
 * @author krzysztof
 */
public class Rain extends Condition {

    public Rain(List<Cloud> clouds, TimeFactory timeFactory, Configuration.Algorithm algorithmConfiguration) {
        super(clouds, timeFactory, algorithmConfiguration);
    }

    @Override
    public String getName() {
        return "Rain";
    }

    @Override
    public RegressionPoint createRegressionPoint(Cloud cloud) {
        return new RegressionPoint(cloud.getFutureRainDistance(), cloud.getTime());
    }

    @Override
    public List<Cloud> filterCandidates(List<Cloud> clouds) {
        return this.filters.filterRainCandidates(clouds);
    }
}
