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

    @Test
	public void testCompare() {
		List<AproximationResult> results = new ArrayList<>();
		results.add(new AproximationResult(AproximationResultType.SUN_UNKNOWN));
		results.add(new AproximationResult(AproximationResultType.RAIN_UNSURE));
		results.add(new AproximationResult(AproximationResultType.SUN_UNSURE));
		results.add(new AproximationResult(AproximationResultType.RAIN_UNKNOWN));
		results.add(new AproximationResult(AproximationResultType.SUN_AT_TIME, TimeUtils.parseInTest("28/04/2015 12:00", configuration)));
		results.add(new AproximationResult(AproximationResultType.RAIN_AT_TIME, TimeUtils.parseInTest("28/04/2015 17:00", configuration)));
		results.add(new AproximationResult(AproximationResultType.RAIN_AT_TIME, TimeUtils.parseInTest("28/04/2015 18:00", configuration)));
		results.add(new AproximationResult(AproximationResultType.SUN_AT_TIME, TimeUtils.parseInTest("28/04/2015 11:00", configuration)));

		Collections.sort(results, new AproximationResultWeatherDominanceComparator());

		assertEquals(new AproximationResult(AproximationResultType.RAIN_AT_TIME, TimeUtils.parseInTest("28/04/2015 17:00", configuration)), results.get(0));
		assertEquals(new AproximationResult(AproximationResultType.RAIN_AT_TIME, TimeUtils.parseInTest("28/04/2015 18:00", configuration)), results.get(1));
		assertEquals(new AproximationResult(AproximationResultType.SUN_AT_TIME, TimeUtils.parseInTest("28/04/2015 11:00", configuration)), results.get(2));
		assertEquals(new AproximationResult(AproximationResultType.SUN_AT_TIME, TimeUtils.parseInTest("28/04/2015 12:00", configuration)), results.get(3));
		assertEquals(new AproximationResult(AproximationResultType.RAIN_UNSURE), results.get(4));
		assertEquals(new AproximationResult(AproximationResultType.SUN_UNSURE), results.get(5));
		assertEquals(new AproximationResult(AproximationResultType.RAIN_UNKNOWN), results.get(6));
		assertEquals(new AproximationResult(AproximationResultType.SUN_UNKNOWN), results.get(7));
	}

}
