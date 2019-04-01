package org.ksapala.rainaproximator.aproximation.angle;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AngleTest {

    @Test
    public void create() {
        assertEquals(300, Angle.create(100, 200), 0.0);
        assertEquals(40, Angle.create(100, 300), 0.0);
        assertEquals(50, Angle.create(100, -50), 0.0);
        assertEquals(260, Angle.create(100, -200), 0.0);

        assertEquals(0, Angle.create(100, -100), 0.0);
        assertEquals(0, Angle.create(100, 260), 0.0);
        assertEquals(359, Angle.create(0, -1), 0.0);
        assertEquals(0, Angle.create(0, 360), 0.0);
    }

    @Test
    public void createDelta() {
        int[] deltaAngles = new int[] {7, 23, -2, -8};
        int base = 1;

        // 7, 23, -2, -8
        // 8, 24, -1, -7
        // -7, -1, 8, 24
        // 353, 359, 8, 24
        int[] expected = new int[] {1, 8, 24, 353, 359};
        assertArrayEquals(expected, Angle.create(base, deltaAngles));
    }
}