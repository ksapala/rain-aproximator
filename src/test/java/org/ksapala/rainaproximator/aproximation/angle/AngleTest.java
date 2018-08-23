package org.ksapala.rainaproximator.aproximation.angle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AngleTest {

    @Autowired
    private Angle angle;

    @Test
    public void create() {
        assertEquals(300, angle.create(100, 200), 0.0);
        assertEquals(40, angle.create(100, 300), 0.0);
        assertEquals(50, angle.create(100, -50), 0.0);
        assertEquals(260, angle.create(100, -200), 0.0);

        assertEquals(0, angle.create(100, -100), 0.0);
        assertEquals(0, angle.create(100, 260), 0.0);
        assertEquals(359, angle.create(0, -1), 0.0);
        assertEquals(0, angle.create(0, 360), 0.0);
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
        assertArrayEquals(expected, angle.create(base, deltaAngles));
    }
}