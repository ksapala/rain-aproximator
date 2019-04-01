package org.ksapala.rainaproximator.aproximation.result;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author krzysztof
 */
public class AccuracyTest {

    @Test
    public void shouldGetVelocityMinutes() {
        // given

        // when
        double velocityMinutes0 = Accuracy.getVelocityMinutes(0, 10);
        double velocityMinutes10 = Accuracy.getVelocityMinutes(10, 10);
        double velocityMinutes20 = Accuracy.getVelocityMinutes(20, 10);
        double velocityMinutes30 = Accuracy.getVelocityMinutes(30, 10);
        double velocityMinutes45 = Accuracy.getVelocityMinutes(45, 10);
        double velocityMinutes60 = Accuracy.getVelocityMinutes(60, 10);
        double velocityMinutes70 = Accuracy.getVelocityMinutes(70, 10);
        double velocityMinutes90 = Accuracy.getVelocityMinutes(90, 10);
        double velocityMinutes100 = Accuracy.getVelocityMinutes(100, 10);
        double velocityMinutes135 = Accuracy.getVelocityMinutes(135, 10);
        double velocityMinutes160 = Accuracy.getVelocityMinutes(160, 10);
        double velocityMinutes180 = Accuracy.getVelocityMinutes(180, 10);
        double velocityMinutes200 = Accuracy.getVelocityMinutes(200, 10);

        // then
        assertEquals(10, velocityMinutes0, 0.001);
        assertTrue(velocityMinutes10 < velocityMinutes20);
        assertTrue(velocityMinutes20 < velocityMinutes30);
        assertTrue(velocityMinutes30 < velocityMinutes45);
        assertEquals(10 * Math.sqrt(2), velocityMinutes45, 0.001);
        assertTrue(velocityMinutes45 > velocityMinutes60);
        assertTrue(velocityMinutes60 > velocityMinutes70);
        assertTrue(velocityMinutes70 > velocityMinutes90);
        assertEquals(10, velocityMinutes90, 0.001);
        assertTrue(velocityMinutes90 < velocityMinutes100);
        assertTrue(velocityMinutes100 < velocityMinutes135);
        assertTrue(velocityMinutes135 > velocityMinutes160);
        assertTrue(velocityMinutes160 > velocityMinutes180);
        assertTrue(velocityMinutes180 < velocityMinutes200);
    }
}