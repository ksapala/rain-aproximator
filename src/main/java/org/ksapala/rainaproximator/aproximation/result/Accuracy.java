package org.ksapala.rainaproximator.aproximation.result;

import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.angle.Angle;

import java.util.Objects;


/**
 * Accuracy:
 *  + 0.5 px for cloud and 0.5 px for user location = 1px = time for flying over 1px = velocity
 *  + rSquare for aproximation
 */
@Getter
public class Accuracy implements Comparable<Accuracy>{

    private double rSquare;
    private double minutes;
    private double velocityMinutes;
    private double standardDeviationMinutes;
    private int poinsCount;

    public Accuracy(double rSquare, double velocityMinutes, double standardDeviationMinutes, int pointsCount) {
        this.rSquare = rSquare;
        this.minutes = velocityMinutes + standardDeviationMinutes;
        this.velocityMinutes = velocityMinutes;
        this.standardDeviationMinutes = standardDeviationMinutes;
        this.poinsCount = pointsCount;
    }

    public static Accuracy of(double rSquare, int angle, double velocity, double differencesStandardDeviation, int pointsCount) {
        double velocityMinutes = getVelocityMinutes(angle, velocity);
        double standardDeviationMinutes = differencesStandardDeviation * velocity;
        return new Accuracy(rSquare, velocityMinutes, standardDeviationMinutes, pointsCount);
    }

    static double getVelocityMinutes(int angle, double velocity) {
        Angle ang = new Angle(angle);
        if (ang.isN() || ang.isS()) {
            return Math.abs(velocity) * Math.sqrt(1 + Math.pow(Math.tan(Math.toRadians(angle)), 2));
        }
        return Math.abs(velocity) * Math.sqrt(1 + Math.pow(1 / Math.tan(Math.toRadians(angle)), 2));
    }

    // 1.0(3) 0.9, 0.8, 0.7, 1.0(2), 1.0(2)
    @Override
    public int compareTo(Accuracy other) {
        boolean firstInnacurate = this.rSquare == 1 && this.poinsCount < 3;
        boolean secondInnacurate = other.rSquare == 1 && other.poinsCount < 3;

        if (firstInnacurate && !secondInnacurate) {
            return 1;
        }
        if (!firstInnacurate && secondInnacurate) {
            return -1;
        }
        return -Double.compare(this.rSquare, other.rSquare);
    }

    @Override
    public String toString() {
        return Double.toString(rSquare);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accuracy accuracy = (Accuracy) o;
        return Double.compare(accuracy.rSquare, rSquare) == 0 &&
                poinsCount == accuracy.poinsCount;
    }

}
