package org.ksapala.rainaproximator.aproximation.debug;

import lombok.Getter;
import lombok.Setter;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Debug {

    @Setter
    private String angle;

    @Setter
    private String performance;

    private RegressionDebug regressionDebug;
    private List<CloudLineDebug> cloudLines;

    public Debug() {
    }

    public void add(List<CloudLine> cloudLines, RegressionDebug regressionDebug) {
        this.regressionDebug = regressionDebug;
        this.cloudLines = IntStream
                .range(0, Math.min(cloudLines.size(), regressionDebug.whichRegressionPointsAreTaken().length))
                .mapToObj(i -> new CloudLineDebug(
                        regressionDebug.whichRegressionPointsAreTaken()[i] ? "TAKEN" : "",
                        cloudLines.get(i).getLineAsString(),
                        cloudLines.get(i).getTime()))
                .collect(Collectors.toList());

    }
}
