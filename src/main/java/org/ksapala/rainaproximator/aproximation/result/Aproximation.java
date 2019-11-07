package org.ksapala.rainaproximator.aproximation.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class Aproximation {

    private final int angle;
    private final AproximationResult aproximationResult;
    private final Accuracy accuracy;

    @Override
    public String toString() {
        return "Angle: " + angle + ". " + aproximationResult.toString() + " Accuracy: " + accuracy;
    }
}
