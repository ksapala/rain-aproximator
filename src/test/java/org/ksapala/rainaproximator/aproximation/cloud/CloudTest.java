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
public class CloudTest {

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
		Cloud cloud = testUtils.stringToCloud(line);
		
		long start = System.currentTimeMillis();
		cloud.replacePattern(new boolean[] {true, false, false, true}, new boolean[] {true, true, true, true});
		System.out.println("boolean replace time: " + (System.currentTimeMillis() - start));

		assertEquals(getLongLineExpectedAfterReplace(), cloud.getLineAsString());
	}
	
	@Test
	public void testSmoothLineSimple() {
		String lineeeee = "#.#..#";
		String expected = "######";
		Cloud cloud = testUtils.stringToCloud(lineeeee);
		
		cloud.smoothLine();
		assertEquals(expected, cloud.getLineAsString());
	}
	
	@Test
	public void testSmoothLineSimple2() {
		String lineeeee = "#.#.#.#";
		String expected = "#######";
		Cloud cloud = testUtils.stringToCloud(lineeeee);
		
		cloud.smoothLine();
		assertEquals(expected, cloud.getLineAsString());
	}

    @Test
    public void testSmoothLine() {
        String lineeeee = "##...........##########.#########..########.....##########...............#....##......####.......#.#.#.#.#.#..#..#..#";
        String expected = ".............#############################################.......................................####################";
        Cloud cloud = testUtils.stringToCloud(lineeeee);

        cloud.smoothLine();
        assertEquals(expected, cloud.getLineAsString());
    }


    @Test
    public void testSmoothMaxHoleLength() {
        String lineeeee = "............###########..................##########....................";
        String expected = "............###########................................................";
        Cloud cloud = testUtils.stringToCloud(lineeeee);

        cloud.smoothLine();
        assertEquals(expected, cloud.getLineAsString());
    }

    @Test
    public void testSmoothStartEnd() {
        String lineeeee = "##.###.#.##.##......................#..#.#.#.##...#..";
        String expected = "##############.......................................";
        Cloud cloud = testUtils.stringToCloud(lineeeee);

        cloud.smoothLine();
        assertEquals(expected, cloud.getLineAsString());
    }

    @Test
    public void testGetDistancesSimple() {
        Cloud cloud = testUtils.stringToCloud("##....");

        assertEquals(new Distance(0), cloud.getRainDistance());
        assertEquals(new Distance(2), cloud.getSunDistance());
    }

    @Test
    public void testGetDistancesInfinityAndZero() {
        Cloud cloud = testUtils.stringToCloud("......");

        assertEquals(Distance.INFINITY, cloud.getRainDistance());
        assertEquals(new Distance(0), cloud.getSunDistance());
    }

    @Test
    public void testGetDistancesFuture() {
        Cloud cloud = testUtils.stringToCloud("##..##..#.");

        assertEquals(new Distance(4), cloud.getFutureRainDistance());
        assertEquals(new Distance(2), cloud.getFutureSunDistance());
    }

    @Test
    public void testIsFutureDistanceInfinity() {
        Cloud cloud = testUtils.stringToCloud("##...");

        assertTrue(cloud.isFutureRainDistanceInfinity());
        assertFalse(cloud.isFutureSunDistanceInfinity());
    }

    @Test
    public void testIsFutureDistanceBothInfinity() {
        Cloud cloud = testUtils.stringToCloud(".....");

        assertTrue(cloud.isFutureRainDistanceInfinity());
        assertTrue(cloud.isFutureSunDistanceInfinity());
    }

}
