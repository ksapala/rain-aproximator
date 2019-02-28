package org.ksapala.rainaproximator.aproximation.debug;

import lombok.Getter;
import lombok.Setter;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;

import java.util.List;

@Setter
@Getter
public class Debug {

    private String angle;
    private String performance;
    private RegressionDebug regressionDebug;
    private List<CloudLine> cloudLines;

    public Debug() {
    }


}
