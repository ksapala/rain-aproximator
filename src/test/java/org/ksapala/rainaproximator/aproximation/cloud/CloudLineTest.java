package org.ksapala.rainaproximator.aproximation.cloud;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.scan.Scanner;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudLineTest {

    @Autowired
    private Configuration configuration;

    @MockBean
    private Scanner scanner;

    private TestUtils testUtils;

	@Before
	public void setUp() {
        testUtils = new TestUtils(configuration);
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
		CloudLine cloudLine = testUtils.stringToCloudLine(line);
		
		long start = System.currentTimeMillis();
		cloudLine.replacePattern(new boolean[] {true, false, false, true}, new boolean[] {true, true, true, true});
		System.out.println("boolean replace time: " + (System.currentTimeMillis() - start));

		assertEquals(getLongLineExpectedAfterReplace(), cloudLine.getLineAsString());
	}
	
	@Test
	public void testSmoothLineSimple() {
		String lineeeee = "#.#..#";
		String expected = "######";
		CloudLine cloudLine = testUtils.stringToCloudLine(lineeeee);
		
		cloudLine.smoothLine();
		assertEquals(expected, cloudLine.getLineAsString());
	}
	
	@Test
	public void testSmoothLineSimple2() {
		String lineeeee = "#.#.#.#";
		String expected = "#######";
		CloudLine cloudLine = testUtils.stringToCloudLine(lineeeee);
		
		cloudLine.smoothLine();
		assertEquals(expected, cloudLine.getLineAsString());
	}

    @Test
    public void testSmoothLine() {
        String lineeeee = "##...........##########.#########..########.....##########...............#....##......####.......#.#.#.#.#.#..#..#..#";
        String expected = ".............#############################################.......................................####################";
        CloudLine cloudLine = testUtils.stringToCloudLine(lineeeee);

        cloudLine.smoothLine();
        assertEquals(expected, cloudLine.getLineAsString());
    }


    @Test
    public void testSmoothMaxHoleLength() {
        String lineeeee = "............###########..................##########....................";
        String expected = "............###########................................................";
        CloudLine cloudLine = testUtils.stringToCloudLine(lineeeee);

        cloudLine.smoothLine();
        assertEquals(expected, cloudLine.getLineAsString());
    }

    @Test
    public void testSmoothStartEnd() {
        String lineeeee = "##.###.#.##.##......................#..#.#.#.##...#..";
        String expected = "##############.......................................";
        CloudLine cloudLine = testUtils.stringToCloudLine(lineeeee);

        cloudLine.smoothLine();
        assertEquals(expected, cloudLine.getLineAsString());
    }

    @Test
    public void testGetDistancesSimple() {
        CloudLine cloudLine = testUtils.stringToCloudLine("##....");

        assertEquals(new Distance(0), cloudLine.getRainDistance());
        assertEquals(new Distance(2), cloudLine.getSunDistance());
    }

    @Test
    public void testGetDistancesInfinityAndZero() {
        CloudLine cloudLine = testUtils.stringToCloudLine("......");

        assertEquals(Distance.INFINITY, cloudLine.getRainDistance());
        assertEquals(new Distance(0), cloudLine.getSunDistance());
    }

    @Test
    public void testGetDistancesFuture() {
        CloudLine cloudLine = testUtils.stringToCloudLine("##..##..#.");

        assertEquals(new Distance(4), cloudLine.getFutureRainDistance());
        assertEquals(new Distance(2), cloudLine.getFutureSunDistance());
    }

    @Test
    public void testIsFutureDistanceInfinity() {
        CloudLine cloudLine = testUtils.stringToCloudLine("##...");

        assertTrue(cloudLine.isFutureRainDistanceInfinity());
        assertFalse(cloudLine.isFutureSunDistanceInfinity());
    }

}
