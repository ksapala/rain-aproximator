package org.ksapala.rainaproximator.aproximation.angle;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

@Component
public class Angle {

    /**
     *
     * @param base
     * @param deltaAngles
     * @return
     */
    public int[] create(int base, @NotEmpty int[] deltaAngles) {
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
    public int create(int base, int delta) {
        int result = base + delta;
        if (result < 0) {
            result += 360;
        } else if (result >= 360) {
            result -= 360;
        }
        return result;
    }
}
