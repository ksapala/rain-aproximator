package org.ksapala.rainaproximator.aproximation.debug;

import lombok.Getter;
import lombok.Setter;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;

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
    private List<CloudLineDebug> cloudLines;

    public Debug() {
    }

    public void addInfo(List<CloudLine> filtered, String info) {
        cloudLines.stream()
                .filter(c -> filtered.contains(c.getCloudLine()))
                .forEach(c -> c.appendInfo(info));

    }

    public void setCloudLines(List<CloudLine> cloudLines) {
        this.cloudLines = cloudLines.stream()
                .map(CloudLineDebug::new)
                .collect(Collectors.toList());

    }
}
