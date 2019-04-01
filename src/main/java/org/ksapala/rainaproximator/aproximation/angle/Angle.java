package org.ksapala.rainaproximator.aproximation.angle;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

public class Angle {

    private final double alpha;
    private final double alphaRadians;

    public Angle(double alpha) {
        this.alpha = alpha;
        this.alphaRadians = Math.toRadians(alpha);
    }


    public boolean isN() {
        return alpha >= 315 || alpha < 45;
    }

    public boolean isE() {
        return alpha >= 45 && alpha < 135;
    }

    public boolean isS() {
        return alpha >= 135 && alpha < 225;
    }

    public boolean isW() {
        return alpha >= 225 && alpha < 315;
    }

    /**
     *
     * @param base
     * @param deltaAngles
     * @return
     */
    public static int[] create(int base, @NotEmpty int[] deltaAngles) {
        int[] angles = new int[deltaAngles.length + 1];

        for (int i = 0; i < deltaAngles.length; i++) {
            angles[i] = create(base, deltaAngles[i]);
        }
        angles[deltaAngles.length] = base;
        Arrays.sort(angles);
        return angles;
    }

    /**
     *
     * @param base
     * @param delta
     * @return
     */
    public static int create(int base, int delta) {
        int result = base + delta;
        if (result < 0) {
            result += 360;
        } else if (result >= 360) {
            result -= 360;
        }
        return result;
    }

    public double tan() {
        return Math.tan(alphaRadians);
    }

}
