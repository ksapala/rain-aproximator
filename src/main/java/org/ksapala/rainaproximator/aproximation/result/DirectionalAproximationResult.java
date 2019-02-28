package org.ksapala.rainaproximator.aproximation.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class DirectionalAproximationResult {

    private int angle;
    private AproximationResult aproximationResult;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectionalAproximationResult that = (DirectionalAproximationResult) o;
        return angle == that.angle &&
                Objects.equals(aproximationResult, that.aproximationResult);
    }

    @Override
    public String toString() {
        return "Angle: " + angle + ". " + aproximationResult.toString();
    }
}
