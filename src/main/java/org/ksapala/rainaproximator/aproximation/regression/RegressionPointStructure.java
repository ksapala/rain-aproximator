package org.ksapala.rainaproximator.aproximation.regression;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.aproximation.regression.RegressionPoint;

@AllArgsConstructor
@Getter
public class RegressionPointStructure {

    private RegressionPoint regressionPoint;
    private Cloud cloud;
}
