package org.ksapala.rainaproximator.aproximation.wind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherJson {

    @Getter
    @Setter
    private Wind wind;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Wind {

        @Getter
        @Setter
        private Double deg;
    }
}
