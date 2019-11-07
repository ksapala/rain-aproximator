package org.ksapala.rainaproximator.aproximation.comparator;

import org.junit.Test;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.result.Accuracy;
import org.ksapala.rainaproximator.aproximation.result.Aproximation;
import org.ksapala.rainaproximator.aproximation.result.AproximationResult;
import org.ksapala.rainaproximator.aproximation.result.AproximationResultType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ComparatorModeTimeTest {

    private Aproximation create(AproximationResultType type, String time) {
        return new Aproximation(0, new AproximationResult(type,
                TestUtils.time(time)), new Accuracy(1.0, 0, 0, 10));
    }

    private Aproximation create(AproximationResultType type) {
        return new Aproximation(0, new AproximationResult(type), new Accuracy(1.0, 0, 0, 10));
    }

    @Test
	public void testCompare() {
		List<Aproximation> results = new ArrayList<>();
		results.add(create(AproximationResultType.SUN_UNKNOWN));
        results.add(create(AproximationResultType.SUN_GOES_AWAY));
        results.add(create(AproximationResultType.RAIN_GOES_AWAY));
		results.add(create(AproximationResultType.RAIN_UNKNOWN));
		results.add(create(AproximationResultType.SUN_AT_TIME,"28-04-2015 12:00"));
		results.add(create(AproximationResultType.RAIN_AT_TIME,"28-04-2015 17:00"));
		results.add(create(AproximationResultType.RAIN_AT_TIME,"28-04-2015 18:00"));
		results.add(create(AproximationResultType.SUN_AT_TIME,"28-04-2015 11:00"));

		results.sort(Comparators.modePredictTime());

        assertEquals(create(AproximationResultType.SUN_AT_TIME, "28-04-2015 11:00"), results.get(0));
        assertEquals(create(AproximationResultType.SUN_AT_TIME, "28-04-2015 12:00"), results.get(1));
        assertEquals(create(AproximationResultType.RAIN_AT_TIME, "28-04-2015 17:00"), results.get(2));
        assertEquals(create(AproximationResultType.RAIN_AT_TIME, "28-04-2015 18:00"), results.get(3));
        assertEquals(create(AproximationResultType.SUN_GOES_AWAY), results.get(4));
        assertEquals(create(AproximationResultType.RAIN_GOES_AWAY), results.get(5));
        assertEquals(create(AproximationResultType.SUN_UNKNOWN), results.get(6));
        assertEquals(create(AproximationResultType.RAIN_UNKNOWN), results.get(7));
	}

}
