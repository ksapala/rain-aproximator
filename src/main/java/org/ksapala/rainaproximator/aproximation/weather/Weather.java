package org.ksapala.rainaproximator.aproximation.weather;

import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.utils.TimeFactory;
import org.ksapala.rainaproximator.configuration.Configuration;

import java.util.List;

/**
 * @author krzysztof
 */
public class Weather {

    private List<Cloud> clouds;

    @Getter private Rain rain;
    @Getter private Sun sun;

    public Weather(List<Cloud> clouds, TimeFactory timeFactory, Configuration.Algorithm algorithmConfiguration) {
        this.clouds = clouds;
        this.rain = new Rain(clouds, timeFactory, algorithmConfiguration);
        this.sun = new Sun(clouds, timeFactory, algorithmConfiguration);
    }

    public boolean isSun() {
        if (Cloud.isSun(clouds)) {
            return !rain.startsInMeantime();
        } else {
            return sun.startsInMeantime();
        }
    }
}
