package org.ksapala.rainaproximator.aproximation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.regression.RegressionTimeFactory;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RainAproximatorTest {

	@Autowired
    private Configuration configuration;

    @Autowired
    private RainAproximator rainAproximator;

    @SpyBean
    private RegressionTimeFactory regressionTimeFactory;

    private TestUtils testUtils;


    @Before
	public void setUp() {
        testUtils = new TestUtils(configuration);
        LocalDateTime fixedTime = testUtils.parseInTest("04/02/2115 14:00");
        doReturn(fixedTime).when(regressionTimeFactory).now();

	}

	@Test
	public void testCloudLineFromString() {
		String stringCloudLine = "....#.....####....";
        CloudLine cloudLine = testUtils.stringToCloudLine(stringCloudLine);

		assertEquals(stringCloudLine, cloudLine.getLineAsString());
	}
	
	@Test
	public void testAproximateRainAtTime() {
		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
		CloudLine cloudLine1 = testUtils.stringToCloudLine("....#.......###########...");
		CloudLine cloudLine2 = testUtils.stringToCloudLine("....#.....###########.....");
		CloudLine cloudLine3 = testUtils.stringToCloudLine("........#######.###.......");
		CloudLine cloudLine4 = testUtils.stringToCloudLine("......####.#.####.........");
		CloudLine cloudLine5 = testUtils.stringToCloudLine("....####.####.#. ..........");

		cloudLine1.setTime(testUtils.parseInTest("04/02/2115 13:15"));
		cloudLine2.setTime(testUtils.parseInTest("04/02/2115 13:25"));
		cloudLine3.setTime(testUtils.parseInTest("04/02/2115 13:35"));
		cloudLine4.setTime(testUtils.parseInTest("04/02/2115 13:45"));
		cloudLine5.setTime(testUtils.parseInTest("04/02/2115 13:55"));
		LocalDateTime expectedTime = testUtils.parseInTest("04/02/2115 14:15");

		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);

		AproximationResult aproximationResult = rainAproximator.aproximate(cloudLines);
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.RAIN_AT_TIME;
		LocalDateTime actualTime = aproximationResult.getPredictTime();

		assertEquals(expectedType, actualType);
		assertEquals(expectedTime, actualTime);
		
	}
	
	@Test
	public void testAproximateSunAtTime() {
		List<CloudLine> cloudLines = new ArrayList<>();
		CloudLine cloudLine1 = testUtils.stringToCloudLine("######################.");
		CloudLine cloudLine2 = testUtils.stringToCloudLine("##################.....");
		CloudLine cloudLine3 = testUtils.stringToCloudLine("##############.........");
		CloudLine cloudLine4 = testUtils.stringToCloudLine("##########.............");
		CloudLine cloudLine5 = testUtils.stringToCloudLine("######.................");

		cloudLine1.setTime(testUtils.parseInTest("04/02/2115 13:15"));
		cloudLine2.setTime(testUtils.parseInTest("04/02/2115 13:25"));
		cloudLine3.setTime(testUtils.parseInTest("04/02/2115 13:35"));
		cloudLine4.setTime(testUtils.parseInTest("04/02/2115 13:45"));
		cloudLine5.setTime(testUtils.parseInTest("04/02/2115 13:55"));
        LocalDateTime expectedTime = testUtils.parseInTest("04/02/2115 14:10");

		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);

		AproximationResult aproximationResult = rainAproximator.aproximate(cloudLines);
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.SUN_AT_TIME;
        LocalDateTime actualTime = aproximationResult.getPredictTime();

		assertEquals(expectedType, actualType);
		assertEquals(expectedTime, actualTime);
	}
	
	@Test
	public void testAproximateRainUnknown() {
		List<CloudLine> cloudLines = new ArrayList<>();
		CloudLine cloudLine1 = testUtils.stringToCloudLine("...................");
		CloudLine cloudLine2 = testUtils.stringToCloudLine("....##########.....");
		CloudLine cloudLine3 = testUtils.stringToCloudLine(".........#.........");
		CloudLine cloudLine4 = testUtils.stringToCloudLine(".##................");
		CloudLine cloudLine5 = testUtils.stringToCloudLine("...................");

		cloudLine1.setTime(testUtils.parseInTest("04/02/2115 13:15"));
		cloudLine2.setTime(testUtils.parseInTest("04/02/2115 13:25"));
		cloudLine3.setTime(testUtils.parseInTest("04/02/2115 13:35"));
		cloudLine4.setTime(testUtils.parseInTest("04/02/2115 13:45"));
		cloudLine5.setTime(testUtils.parseInTest("04/02/2115 13:55"));

		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);

		AproximationResult aproximationResult = rainAproximator.aproximate(cloudLines);
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.RAIN_UNKNOWN;
		LocalDateTime actualTime = aproximationResult.getPredictTime();

		assertNull(actualTime);
		assertEquals(expectedType, actualType);
	}
	
	@Test
	public void testAproximateSunUnknown() {
		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
		CloudLine cloudLine1 = testUtils.stringToCloudLine("#######..##########");
		CloudLine cloudLine2 = testUtils.stringToCloudLine("###################");
		CloudLine cloudLine3 = testUtils.stringToCloudLine(".........#.........");
		CloudLine cloudLine4 = testUtils.stringToCloudLine("###################");
		CloudLine cloudLine5 = testUtils.stringToCloudLine("#####.#########..##");

		cloudLine1.setTime(testUtils.parseInTest("04/02/2115 13:15"));
		cloudLine2.setTime(testUtils.parseInTest("04/02/2115 13:25"));
		cloudLine3.setTime(testUtils.parseInTest("04/02/2115 13:35"));
		cloudLine4.setTime(testUtils.parseInTest("04/02/2115 13:45"));
		cloudLine5.setTime(testUtils.parseInTest("04/02/2115 13:55"));

		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);

		AproximationResult aproximationResult = rainAproximator.aproximate(cloudLines);
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.SUN_UNKNOWN;
        LocalDateTime actualTime = aproximationResult.getPredictTime();

		assertNull(actualTime);
		assertEquals(expectedType, actualType);
	}
	
	@Test
	public void testAproximateSunUnknownDateForPast() {
		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
		CloudLine cloudLine1 = testUtils.stringToCloudLine("######.............");
		CloudLine cloudLine2 = testUtils.stringToCloudLine("#########..........");
		CloudLine cloudLine3 = testUtils.stringToCloudLine("############.......");
		CloudLine cloudLine4 = testUtils.stringToCloudLine("###############....");
		CloudLine cloudLine5 = testUtils.stringToCloudLine("#####.###########..");

		cloudLine1.setTime(testUtils.parseInTest("04/02/2115 13:15"));
		cloudLine2.setTime(testUtils.parseInTest("04/02/2115 13:25"));
		cloudLine3.setTime(testUtils.parseInTest("04/02/2115 13:35"));
		cloudLine4.setTime(testUtils.parseInTest("04/02/2115 13:45"));
		cloudLine5.setTime(testUtils.parseInTest("04/02/2115 13:55"));


		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);

		AproximationResult aproximationResult = rainAproximator.aproximate(cloudLines);
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.SUN_UNSURE;
        LocalDateTime actualTime = aproximationResult.getPredictTime();

		assertNull(actualTime);
		assertEquals(expectedType, actualType);
	}
	
}
