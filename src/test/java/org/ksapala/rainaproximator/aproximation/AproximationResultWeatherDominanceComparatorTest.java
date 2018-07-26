package org.ksapala.rainaproximator.aproximation;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.ksapala.rainaproximator.BaseTest;
import org.ksapala.rainaproximator.util.Utils;

public class AproximationResultWeatherDominanceComparatorTest extends BaseTest {

	@Test
	public void testCompare() throws ParseException {
		List<AproximationResult> results = new ArrayList<AproximationResult>();
		results.add(new AproximationResult(AproximationResultType.SUN_UNKNOWN));
		results.add(new AproximationResult(AproximationResultType.RAIN_UNSURE));
		results.add(new AproximationResult(AproximationResultType.SUN_UNSURE));
		results.add(new AproximationResult(AproximationResultType.RAIN_UNKNOWN));
		results.add(new AproximationResult(AproximationResultType.SUN_AT_TIME, Utils.parse("28/04/2015 12:00")));
		results.add(new AproximationResult(AproximationResultType.RAIN_AT_TIME, Utils.parse("28/04/2015 17:00")));
		results.add(new AproximationResult(AproximationResultType.RAIN_AT_TIME, Utils.parse("28/04/2015 18:00")));
		results.add(new AproximationResult(AproximationResultType.SUN_AT_TIME, Utils.parse("28/04/2015 11:00")));
		
		Collections.sort(results, new AproximationResultWeatherDominanceComparator());
		
		assertEquals(new AproximationResult(AproximationResultType.RAIN_AT_TIME, Utils.parse("28/04/2015 17:00")), results.get(0));
		assertEquals(new AproximationResult(AproximationResultType.RAIN_AT_TIME, Utils.parse("28/04/2015 18:00")), results.get(1));
		assertEquals(new AproximationResult(AproximationResultType.SUN_AT_TIME, Utils.parse("28/04/2015 11:00")), results.get(2));
		assertEquals(new AproximationResult(AproximationResultType.SUN_AT_TIME, Utils.parse("28/04/2015 12:00")), results.get(3));
		assertEquals(new AproximationResult(AproximationResultType.RAIN_UNSURE), results.get(4));
		assertEquals(new AproximationResult(AproximationResultType.SUN_UNSURE), results.get(5));
		assertEquals(new AproximationResult(AproximationResultType.RAIN_UNKNOWN), results.get(6));
		assertEquals(new AproximationResult(AproximationResultType.SUN_UNKNOWN), results.get(7));
	}

}
