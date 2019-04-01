package org.ksapala.rainaproximator.aproximation.result;

import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.angle.Angle;


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

    public Accuracy(double rSquare, double minutes) {
        this.rSquare = rSquare;
        this.minutes = minutes;
    }

    public Accuracy(double rSquare, double velocityMinutes, double standardDeviationMinutes) {
        this.rSquare = rSquare;
        this.minutes = velocityMinutes + standardDeviationMinutes;
        this.velocityMinutes = velocityMinutes;
        this.standardDeviationMinutes = standardDeviationMinutes;
    }

    public static Accuracy of(double rSquare, int angle, double velocity, double differencesStandardDeviation) {
        double velocityMinutes = getVelocityMinutes(angle, velocity);
        double standardDeviationMinutes = differencesStandardDeviation * velocity;
        return new Accuracy(rSquare, velocityMinutes, standardDeviationMinutes);
    }

    static double getVelocityMinutes(int angle, double velocity) {
        Angle ang = new Angle(angle);
        if (ang.isN() || ang.isS()) {
            return Math.abs(velocity) * Math.sqrt(1 + Math.pow(Math.tan(Math.toRadians(angle)), 2));
        }
        return Math.abs(velocity) * Math.sqrt(1 + Math.pow(1 / Math.tan(Math.toRadians(angle)), 2));
    }

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
