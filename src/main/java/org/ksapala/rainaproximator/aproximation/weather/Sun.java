package org.ksapala.rainaproximator.aproximation.weather;

import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.aproximation.regression.RegressionPoint;
import org.ksapala.rainaproximator.aproximation.regression.RegressionTimeFactory;
import org.ksapala.rainaproximator.configuration.Configuration;

import java.util.List;

/**
 * @author krzysztof
 */
public class Sun extends Condition {

    public Sun(List<Cloud> clouds, RegressionTimeFactory regressionTimeFactory, Configuration.Algorithm algorithmConfiguration) {
        super(clouds, regressionTimeFactory, algorithmConfiguration);
    }

    @Override
    public String getName() {
        return "Sun";
    }

    @Override
    public RegressionPoint createRegressionPoint(Cloud cloud) {
        return new RegressionPoint(cloud.getFutureSunDistance(), cloud.getTime());
    }

    @Override
    public List<Cloud> filterCandidates(List<Cloud> clouds) {
        return this.filters.filterSunCandidates(clouds);
    }
}
