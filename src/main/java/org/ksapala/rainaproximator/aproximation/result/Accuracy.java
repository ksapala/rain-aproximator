package org.ksapala.rainaproximator.aproximation.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Accuracy implements Comparable<Accuracy>{

    @Getter
    private double rSquare;

    // 0.9, 0.8, 0.7, 1.0, 1.0
    @Override
    public int compareTo(Accuracy other) {
        if (this.rSquare == 1.0 && other.rSquare < 1.0) {
            return 1;
        }
        if (this.rSquare < 1.0 && other.rSquare == 1.0) {
            return -1;
        }
        return -Double.compare(this.rSquare, other.rSquare);
    }

    @Override
    public String toString() {
        return Double.toString(rSquare);
    }
}
