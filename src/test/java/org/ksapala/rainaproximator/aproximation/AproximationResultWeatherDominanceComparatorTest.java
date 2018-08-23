package org.ksapala.rainaproximator.aproximation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AproximationResultWeatherDominanceComparatorTest {

    @Autowired
    private Configuration configuration;

    private DirectionalAproximationResult create(AproximationResultType type, String time) {
        return new DirectionalAproximationResult(0, new AproximationResult(type, TimeUtils.parseInTest(time, configuration)));
    }

    private DirectionalAproximationResult create(AproximationResultType type) {
        return new DirectionalAproximationResult(0, new AproximationResult(type));
    }

    @Test
	public void testCompare() {
		List<DirectionalAproximationResult> results = new ArrayList<>();
		results.add(create(AproximationResultType.SUN_UNKNOWN));
		results.add(create(AproximationResultType.RAIN_UNSURE));
		results.add(create(AproximationResultType.SUN_UNSURE));
		results.add(create(AproximationResultType.RAIN_UNKNOWN));
		results.add(create(AproximationResultType.SUN_AT_TIME,"28/04/2015 12:00"));
		results.add(create(AproximationResultType.RAIN_AT_TIME,"28/04/2015 17:00"));
		results.add(create(AproximationResultType.RAIN_AT_TIME,"28/04/2015 18:00"));
		results.add(create(AproximationResultType.SUN_AT_TIME,"28/04/2015 11:00"));

		Collections.sort(results, new AproximationResultWeatherDominanceComparator());

		assertEquals(create(AproximationResultType.RAIN_AT_TIME, "28/04/2015 17:00"), results.get(0));
		assertEquals(create(AproximationResultType.RAIN_AT_TIME, "28/04/2015 18:00"), results.get(1));
		assertEquals(create(AproximationResultType.SUN_AT_TIME, "28/04/2015 11:00"), results.get(2));
		assertEquals(create(AproximationResultType.SUN_AT_TIME, "28/04/2015 12:00"), results.get(3));
		assertEquals(create(AproximationResultType.RAIN_UNSURE), results.get(4));
		assertEquals(create(AproximationResultType.SUN_UNSURE), results.get(5));
		assertEquals(create(AproximationResultType.RAIN_UNKNOWN), results.get(6));
		assertEquals(create(AproximationResultType.SUN_UNKNOWN), results.get(7));
	}

}
