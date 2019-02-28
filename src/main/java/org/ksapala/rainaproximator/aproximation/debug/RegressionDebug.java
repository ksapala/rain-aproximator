package org.ksapala.rainaproximator.aproximation.debug;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RegressionDebug {

    @Getter
    private double standardDeviation;

    @Getter
    private double slope;

    @Getter
    private double rSquare;

    @Getter
    private int pointsCount;


}
