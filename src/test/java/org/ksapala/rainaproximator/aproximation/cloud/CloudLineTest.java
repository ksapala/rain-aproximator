package org.ksapala.rainaproximator.aproximation.cloud;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.scan.Scanner;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.withmain.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudLineTest {

    @Autowired
    private Configuration configuration;

    @MockBean
    private Scanner scanner;

	@Before
	public void setUp() {
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
		CloudLine cloudLine = TestUtils.stringToCloudLine(configuration.getAlgorithm().getCloud(), line);
		
		long start = System.currentTimeMillis();
		cloudLine.replacePattern(new boolean[] {true, false, false, true}, new boolean[] {true, true, true, true});
		System.out.println("boolean replace time: " + (System.currentTimeMillis() - start));

		assertEquals(getLongLineExpectedAfterReplace(), cloudLine.getLineAsString());
	}
	
	@Test
	public void testSmoothLineSimple() {
		String lineeeee = "#.#..#";
		String expected = "######";
		CloudLine cloudLine = TestUtils.stringToCloudLine(configuration.getAlgorithm().getCloud(), lineeeee);
		
		cloudLine.smoothLine();
		assertEquals(expected, cloudLine.getLineAsString());
	}
	
	@Test
	public void testSmoothLineSimple2() {
		String lineeeee = "#.#.#.#";
		String expected = "#######";
		CloudLine cloudLine = TestUtils.stringToCloudLine(configuration.getAlgorithm().getCloud(), lineeeee);
		
		cloudLine.smoothLine();
		assertEquals(expected, cloudLine.getLineAsString());
	}

    @Test
    public void testSmoothLine() {
        String lineeeee = "##...........##########.#########..########.....##########...............#....##......####.......#.#.#.#.#.#..#..#..#";
        String expected = ".............#############################################.......................................####################";
        CloudLine cloudLine = TestUtils.stringToCloudLine(configuration.getAlgorithm().getCloud(), lineeeee);

        cloudLine.smoothLine();
        assertEquals(expected, cloudLine.getLineAsString());
    }


    @Test
    public void testSmoothMaxHoleLength() {
        String lineeeee = "............###########..................##########....................";
        String expected = "............###########................................................";
        CloudLine cloudLine = TestUtils.stringToCloudLine(configuration.getAlgorithm().getCloud(), lineeeee);

        cloudLine.smoothLine();
        assertEquals(expected, cloudLine.getLineAsString());
    }

    @Test
    public void testSmoothStartEnd() {
        String lineeeee = "##.###.#.##.##......................#..#.#.#.##...#..";
        String expected = "##############.......................................";
        CloudLine cloudLine = TestUtils.stringToCloudLine(configuration.getAlgorithm().getCloud(),lineeeee);

        cloudLine.smoothLine();
        assertEquals(expected, cloudLine.getLineAsString());
    }


}
