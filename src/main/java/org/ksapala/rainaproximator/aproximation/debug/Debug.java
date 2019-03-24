package org.ksapala.rainaproximator.aproximation.debug;

import lombok.Getter;
import lombok.Setter;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Debug {

    @Setter
    private String angle;

    @Setter
    private String performance;

    @Setter
    private RegressionDebug regressionDebug;
    private List<CloudDebug> clouds;

    public Debug() {
    }

    public void addInfo(List<Cloud> filtered, String info) {
        clouds.stream()
                .filter(c -> filtered.contains(c.getCloud()))
                .forEach(c -> c.appendInfo(info));

    }

    public void setClouds(List<Cloud> clouds) {
        this.clouds = clouds.stream()
                .map(CloudDebug::new)
                .collect(Collectors.toList());

    }
}
