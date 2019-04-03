package org.ksapala.rainaproximator.aproximation.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Aproximation {

    private final int angle;
    private final AproximationResult aproximationResult;
    private final Accuracy accuracy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aproximation that = (Aproximation) o;
        return angle == that.angle &&
                Objects.equals(aproximationResult, that.aproximationResult) &&
                Objects.equals(accuracy, that.accuracy);
    }


    @Override
    public String toString() {
        return "Angle: " + angle + ". " + aproximationResult.toString();
    }
}
