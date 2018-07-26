package org.ksapala.rainaproximator.aproximation.cloud;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CloudLineTest {

	@Before
	public void setUp() throws Exception {
	}

	private String getLongLine() {
		String line = "";
		for (int i = 0; i < 200; i++) {
	        line += "##..##.#.#..#.";
        }
		return line;
	}
	
	private String getLongLineExpectedAfterReplace() {
		String line = "";
		for (int i = 0; i < 200; i++) {
	        line += "######.#.####.";
        }
		return line;
	}
	
	@Test
	public void testReplacePatternBoolean() {
		String line = getLongLine();
		CloudLine cloudLine = CloudLine.fromString(line);
		
		long start = System.currentTimeMillis();
		cloudLine.replacePattern(new boolean[] {true, false, false, true}, new boolean[] {true, true, true, true});
		System.out.println("boolean replace time: " + (System.currentTimeMillis() - start));

		assertEquals(getLongLineExpectedAfterReplace(), cloudLine.getLineAsString());
	}
	
	@Test
	public void testSmoothLineSimple() {
		String lineeeee = "#.#..#";
		String expected = "######";
		CloudLine cloudLine = CloudLine.fromString(lineeeee);
		
		cloudLine.smoothLine(algorithmConfiguration.getCloud());
		assertEquals(expected, cloudLine.getLineAsString());
	}
	
	@Test
	public void testSmoothLineSimple2() {
		String lineeeee = "#.#.#.#";
		String expected = "#######";
		CloudLine cloudLine = CloudLine.fromString(lineeeee);
		
		cloudLine.smoothLine(algorithmConfiguration.getCloud());
		assertEquals(expected, cloudLine.getLineAsString());
	}
	
	@Test
	public void testSmoothLine() {
		String lineeeee = "##......##########.#########..########.....##########.............#..#....##......####.......#.#.#.#..#..#..#";
		String expected = "........#############################################........................................#######........#";
		CloudLine cloudLine = CloudLine.fromString(lineeeee);
		
		cloudLine.smoothLine(algorithmConfiguration.getCloud());
		assertEquals(expected, cloudLine.getLineAsString());
	}
}
