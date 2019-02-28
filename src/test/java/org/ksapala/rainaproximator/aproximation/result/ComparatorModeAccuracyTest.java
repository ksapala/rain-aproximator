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

    private DirectionalAproximationResult create(AproximationResultType type, double rSquare, String time) {
        return new DirectionalAproximationResult(0, new AproximationResult(type, new Accuracy(rSquare),
                TimeUtils.parseInTest(time, configuration)));
    }

    private DirectionalAproximationResult create(AproximationResultType type, double rSquare) {
        return new DirectionalAproximationResult(0, new AproximationResult(type, new Accuracy(rSquare)));
    }

    @Test
	public void testCompare() {
		List<DirectionalAproximationResult> results = new ArrayList<>();
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

        results.sort(Comparators.modeAccuracy());

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
