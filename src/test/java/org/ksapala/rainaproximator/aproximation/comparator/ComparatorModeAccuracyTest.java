package org.ksapala.rainaproximator.aproximation.comparator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.comparator.Comparators;
import org.ksapala.rainaproximator.aproximation.result.Accuracy;
import org.ksapala.rainaproximator.aproximation.result.Aproximation;
import org.ksapala.rainaproximator.aproximation.result.AproximationResult;
import org.ksapala.rainaproximator.aproximation.result.AproximationResultType;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ComparatorModeAccuracyTest {

    private Aproximation create(AproximationResultType type, double rSquare, int pointsCount, String time) {
        AproximationResult aproximationResult = new AproximationResult(type, TestUtils.parseInTest(time));
        return create(aproximationResult, rSquare, pointsCount);
    }

    private Aproximation create(AproximationResultType type, double rSquare, int pointsCount) {
        AproximationResult aproximationResult = new AproximationResult(type);
        return create(aproximationResult, rSquare, pointsCount);
    }

    private Aproximation create(AproximationResult aproximationResult, double rSquare, int pointsCount) {
        Accuracy accuracy = new Accuracy(rSquare, 0, 0, pointsCount);
        return new Aproximation(0, aproximationResult, accuracy);
    }

    @Test
    public void testCompareRSquareAndWeatherDominance() {
        // given
        List<Aproximation> results = new ArrayList<>();
        results.add(create(AproximationResultType.SUN_UNKNOWN, 0.9, 3));
        results.add(create(AproximationResultType.SUN_UNSURE, 0.9, 3));
        results.add(create(AproximationResultType.RAIN_UNSURE, 0.9, 3));
        results.add(create(AproximationResultType.RAIN_UNKNOWN, 0.9, 3));
        results.add(create(AproximationResultType.SUN_AT_TIME, 0.7, 3, "28/04/2015 12:00"));
        results.add(create(AproximationResultType.SUN_AT_TIME, 1.0, 3, "28/04/2015 17:00"));
        results.add(create(AproximationResultType.SUN_AT_TIME, 0.8, 3, "28/04/2015 18:00"));
        results.add(create(AproximationResultType.SUN_AT_TIME, 0.9, 3, "28/04/2015 11:00"));
        results.add(create(AproximationResultType.RAIN_AT_TIME, 0.7, 3, "28/04/2015 12:00"));
        results.add(create(AproximationResultType.RAIN_AT_TIME, 1.0, 3, "28/04/2015 16:00"));
        results.add(create(AproximationResultType.RAIN_AT_TIME, 0.8, 3, "28/04/2015 18:00"));
        results.add(create(AproximationResultType.RAIN_AT_TIME, 0.9, 3, "28/04/2015 10:00"));

        // when
        results.sort(Comparators.modeAccuracy());

        // then
        assertEquals(create(AproximationResultType.RAIN_AT_TIME, 1.0, 3, "28/04/2015 16:00"), results.get(0));
        assertEquals(create(AproximationResultType.SUN_AT_TIME, 1.0, 3, "28/04/2015 17:00"), results.get(1));

        assertEquals(create(AproximationResultType.RAIN_AT_TIME, 0.9, 3, "28/04/2015 10:00"), results.get(2));
        assertEquals(create(AproximationResultType.SUN_AT_TIME, 0.9, 3, "28/04/2015 11:00"), results.get(3));

        assertEquals(create(AproximationResultType.SUN_AT_TIME, 0.8, 3, "28/04/2015 18:00"), results.get(4));
        assertEquals(create(AproximationResultType.RAIN_AT_TIME, 0.8, 3, "28/04/2015 18:00"), results.get(5));

        assertEquals(create(AproximationResultType.SUN_AT_TIME, 0.7, 3, "28/04/2015 12:00"), results.get(6));
        assertEquals(create(AproximationResultType.RAIN_AT_TIME, 0.7, 3, "28/04/2015 12:00"), results.get(7));

        assertEquals(create(AproximationResultType.SUN_UNSURE, 0.9, 3), results.get(8));
        assertEquals(create(AproximationResultType.RAIN_UNSURE, 0.9, 3), results.get(9));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 0.9, 3), results.get(10));
        assertEquals(create(AproximationResultType.RAIN_UNKNOWN, 0.9, 3), results.get(11));


    }

    @Test
    public void shouldCompareRSquareAndPointsCount() {
        // given
        List<Aproximation> results = new ArrayList<>();
        results.add(create(AproximationResultType.SUN_UNKNOWN, 1.0, 2));
        results.add(create(AproximationResultType.SUN_UNKNOWN, 0.8, 10));
        results.add(create(AproximationResultType.SUN_UNKNOWN, 0.9, 10));
        results.add(create(AproximationResultType.SUN_UNKNOWN, 1.0, 2));
        results.add(create(AproximationResultType.SUN_UNKNOWN, 1.0, 10));

        // when
        results.sort(Comparators.modeAccuracy());

        // then
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 1.0, 10), results.get(0));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 0.9, 10), results.get(1));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 0.8, 10), results.get(2));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 1.0, 2), results.get(3));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 1.0, 2), results.get(4));

    }

    @Test
    public void shouldComparePlaceInnacurateAsLast() {
        // given
        List<Aproximation> results = new ArrayList<>();
        results.add(create(AproximationResultType.RAIN_UNKNOWN, Double.NaN, 0));
        results.add(create(AproximationResultType.RAIN_UNKNOWN, Double.NaN, 0));
        results.add(create(AproximationResultType.RAIN_UNKNOWN, Double.NaN, 0));
        results.add(create(AproximationResultType.RAIN_AT_TIME, 1.0, 2, "28/04/2015 12:00"));
        results.add(create(AproximationResultType.RAIN_AT_TIME, 0.9, 3, "28/04/2015 12:00"));
        results.add(create(AproximationResultType.RAIN_AT_TIME, 1.0, 3, "28/04/2015 12:00"));
        results.add(create(AproximationResultType.RAIN_UNSURE, 1.0, 2));
        results.add(create(AproximationResultType.RAIN_UNSURE, 0.9, 3));
        results.add(create(AproximationResultType.RAIN_UNSURE, 1.0, 3));

        // when
        results.sort(Comparators.modeAccuracy());

        // then
        assertEquals(create(AproximationResultType.RAIN_AT_TIME, 1.0, 3, "28/04/2015 12:00"), results.get(0));
        assertEquals(create(AproximationResultType.RAIN_AT_TIME, 0.9, 3, "28/04/2015 12:00"), results.get(1));
        assertEquals(create(AproximationResultType.RAIN_UNSURE, 1.0, 3), results.get(2));
        assertEquals(create(AproximationResultType.RAIN_UNSURE, 0.9, 3), results.get(3));
        assertEquals(create(AproximationResultType.RAIN_UNKNOWN, Double.NaN, 0), results.get(4));
        assertEquals(create(AproximationResultType.RAIN_UNKNOWN, Double.NaN, 0), results.get(5));
        assertEquals(create(AproximationResultType.RAIN_UNKNOWN, Double.NaN, 0), results.get(6));
        assertEquals(create(AproximationResultType.RAIN_AT_TIME, 1.0, 2, "28/04/2015 12:00"), results.get(7));
        assertEquals(create(AproximationResultType.RAIN_UNSURE, 1.0, 2), results.get(8));
    }

    @Test
    public void shouldCompareHeavyRainUnsure() {
        // given
        List<Aproximation> results = new ArrayList<>();
        results.add(create(AproximationResultType.RAIN_AT_TIME, 0.6, 3, "28/04/2015 12:00"));
        results.add(create(AproximationResultType.RAIN_UNSURE, 0.988, 3));
        results.add(create(AproximationResultType.RAIN_UNSURE, 0.7, 3));
        results.add(create(AproximationResultType.RAIN_UNSURE, 0.7, 3));

        // when
        results.sort(Comparators.modeAccuracy());

        // then
        assertEquals(create(AproximationResultType.RAIN_AT_TIME, 0.6, 3, "28/04/2015 12:00"), results.get(0));
        assertEquals(create(AproximationResultType.RAIN_UNSURE, 0.988, 3), results.get(1));
        assertEquals(create(AproximationResultType.RAIN_UNSURE, 0.7, 3), results.get(2));
        assertEquals(create(AproximationResultType.RAIN_UNSURE, 0.7, 3), results.get(3));
    }
}
