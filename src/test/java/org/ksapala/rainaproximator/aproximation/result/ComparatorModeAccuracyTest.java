package org.ksapala.rainaproximator.aproximation.result;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.comparator.Comparators;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComparatorModeAccuracyTest {

    @Autowired
    private Configuration configuration;

    private Aproximation create(AproximationResultType type, double rSquare, String time) {
        AproximationResult aproximationResult = new AproximationResult(type, TimeUtils.parseInTest(time, configuration));
        return create(aproximationResult, rSquare, 2);
    }

    private Aproximation create(AproximationResultType type, double rSquare, int pointsCount) {
        AproximationResult aproximationResult = new AproximationResult(type);
        return create(aproximationResult, rSquare, pointsCount);
    }

    private Aproximation create(AproximationResultType type, double rSquare) {
        AproximationResult aproximationResult = new AproximationResult(type);
        return create(aproximationResult, rSquare, 2);
    }

    private Aproximation create(AproximationResult aproximationResult, double rSquare, int pointsCount) {
        Accuracy accuracy = new Accuracy(rSquare, 0, 0, pointsCount);
        return new Aproximation(0, aproximationResult, accuracy);
    }

    @Test
    public void shouldCompareAccuracyParameters() {
        // given
        List<Aproximation> results = new ArrayList<>();
        results.add(create(AproximationResultType.SUN_UNKNOWN,1.0,1));
        results.add(create(AproximationResultType.SUN_UNKNOWN,0.8,10));
        results.add(create(AproximationResultType.SUN_UNKNOWN,0.9,10));
        results.add(create(AproximationResultType.SUN_UNKNOWN,1.0,2));
        results.add(create(AproximationResultType.SUN_UNKNOWN,1.0,3));

        // when
        results.sort(Comparators.modeAccuracy());

        // than
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 1.0, 3), results.get(0));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 0.9, 10), results.get(1));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 0.8, 10), results.get(2));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 1.0, 1), results.get(3));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 1.0, 2), results.get(4));

    }

    @Test
	public void testCompareOnlyRSquare() {
        // given
		List<Aproximation> results = new ArrayList<>();
		results.add(create(AproximationResultType.SUN_UNKNOWN,0.9));
        results.add(create(AproximationResultType.SUN_UNSURE,0.9));
        results.add(create(AproximationResultType.RAIN_UNSURE,0.9));
		results.add(create(AproximationResultType.RAIN_UNKNOWN,0.9));
        results.add(create(AproximationResultType.SUN_AT_TIME,0.7, "28/04/2015 12:00"));
        results.add(create(AproximationResultType.SUN_AT_TIME,1.0, "28/04/2015 17:00"));
        results.add(create(AproximationResultType.SUN_AT_TIME,0.8, "28/04/2015 18:00"));
        results.add(create(AproximationResultType.SUN_AT_TIME,0.9, "28/04/2015 11:00"));
        results.add(create(AproximationResultType.RAIN_AT_TIME,0.7, "28/04/2015 12:00"));
        results.add(create(AproximationResultType.RAIN_AT_TIME,1.0, "28/04/2015 17:00"));
        results.add(create(AproximationResultType.RAIN_AT_TIME,0.8, "28/04/2015 18:00"));
        results.add(create(AproximationResultType.RAIN_AT_TIME,0.9, "28/04/2015 11:00"));

        // when
        results.sort(Comparators.modeAccuracy());

        // than
        assertEquals(create(AproximationResultType.RAIN_AT_TIME,0.9, "28/04/2015 11:00"), results.get(0));
        assertEquals(create(AproximationResultType.RAIN_AT_TIME,0.8, "28/04/2015 18:00"), results.get(1));
        assertEquals(create(AproximationResultType.RAIN_AT_TIME,0.7, "28/04/2015 12:00"), results.get(2));
        assertEquals(create(AproximationResultType.RAIN_AT_TIME,1.0, "28/04/2015 17:00"), results.get(3));

        assertEquals(create(AproximationResultType.SUN_AT_TIME,0.9, "28/04/2015 11:00"), results.get(4));
        assertEquals(create(AproximationResultType.SUN_AT_TIME,0.8, "28/04/2015 18:00"), results.get(5));
        assertEquals(create(AproximationResultType.SUN_AT_TIME,0.7, "28/04/2015 12:00"), results.get(6));
        assertEquals(create(AproximationResultType.SUN_AT_TIME,1.0, "28/04/2015 17:00"), results.get(7));

        assertEquals(create(AproximationResultType.RAIN_UNSURE, 0.9), results.get(8));
        assertEquals(create(AproximationResultType.SUN_UNSURE, 0.9), results.get(9));
        assertEquals(create(AproximationResultType.RAIN_UNKNOWN, 0.9), results.get(10));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN, 0.9), results.get(11));
	}

}
