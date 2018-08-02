package org.ksapala.rainaproximator.aproximation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RainAproximatorTest {

	@Autowired
	private Configuration configuration;

	@Before
	public void setUp() {
//		Settings.injectableSettings = new DeveloperSettings();
	}

	@Test
	public void testCloudLineFromString() {
//		String stringCloudLine = "....#.....####....";
//		CloudLine cloudLine = TestUtils.stringToCloudLine(stringCloudLine);
//
//		assertEquals(stringCloudLine, cloudLine.getLineAsString());
	}
	
	@Test
	public void testAproximateRainAtTime() throws ParseException {
//		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
//		CloudLine cloudLine1 = TestUtils.stringToCloudLine("....#.......######...");
//		CloudLine cloudLine2 = TestUtils.stringToCloudLine("....#.....######.....");
//		CloudLine cloudLine3 = TestUtils.stringToCloudLine("........######.......");
//		CloudLine cloudLine4 = TestUtils.stringToCloudLine("......####.#.........");
//		CloudLine cloudLine5 = TestUtils.stringToCloudLine("....####.#...........");
//
//
//
//		cloudLine1.setTime(this.format.parse("04/02/2015 13:15"));
//		cloudLine2.setTime(this.format.parse("04/02/2015 13:25"));
//		cloudLine3.setTime(this.format.parse("04/02/2015 13:35"));
//		cloudLine4.setTime(this.format.parse("04/02/2015 13:45"));
//		cloudLine5.setTime(this.format.parse("04/02/2015 13:55"));
//		Date expectedTime = this.format.parse("04/02/2015 14:15");
//
//		cloudLines.add(cloudLine1);
//		cloudLines.add(cloudLine2);
//		cloudLines.add(cloudLine3);
//		cloudLines.add(cloudLine4);
//		cloudLines.add(cloudLine5);
//
//		AproximationResult aproximationResult = new RainAproximator(configuration).aproximate(cloudLines);
//		assertNotNull(aproximationResult);
//
//		AproximationResultType actualType = aproximationResult.getType();
//		AproximationResultType expectedType = AproximationResultType.RAIN_AT_TIME;
//		Date actualTime = aproximationResult.getPredictTime();
//
//		assertEquals(expectedType, actualType);
//		assertEquals(expectedTime, actualTime);
		
	}
	
	@Test
	public void testAproximateSunAtTime() throws ParseException {
//		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
//		CloudLine cloudLine1 = TestUtils.stringToCloudLine("######################.");
//		CloudLine cloudLine2 = TestUtils.stringToCloudLine("##################.....");
//		CloudLine cloudLine3 = TestUtils.stringToCloudLine("##############.........");
//		CloudLine cloudLine4 = TestUtils.stringToCloudLine("##########.............");
//		CloudLine cloudLine5 = TestUtils.stringToCloudLine("######.................");
//
//		cloudLine1.setTime(this.format.parse("04/02/2015 13:15"));
//		cloudLine2.setTime(this.format.parse("04/02/2015 13:25"));
//		cloudLine3.setTime(this.format.parse("04/02/2015 13:35"));
//		cloudLine4.setTime(this.format.parse("04/02/2015 13:45"));
//		cloudLine5.setTime(this.format.parse("04/02/2015 13:55"));
//		Date expectedTime = this.format.parse("04/02/2015 14:10");
//
//		cloudLines.add(cloudLine1);
//		cloudLines.add(cloudLine2);
//		cloudLines.add(cloudLine3);
//		cloudLines.add(cloudLine4);
//		cloudLines.add(cloudLine5);
//
//		AproximationResult aproximationResult = new RainAproximator(configuration).aproximate(cloudLines);
//		assertNotNull(aproximationResult);
//
//		AproximationResultType actualType = aproximationResult.getType();
//		AproximationResultType expectedType = AproximationResultType.SUN_AT_TIME;
//		Date actualTime = aproximationResult.getPredictTime();
//
//		assertEquals(expectedType, actualType);
//		assertEquals(expectedTime, actualTime);
	}
	
	@Test
	public void testAproximateRainUnknown() throws ParseException {
//		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
//		CloudLine cloudLine1 = TestUtils.stringToCloudLine("...................");
//		CloudLine cloudLine2 = TestUtils.stringToCloudLine("....##########.....");
//		CloudLine cloudLine3 = TestUtils.stringToCloudLine(".........#.........");
//		CloudLine cloudLine4 = TestUtils.stringToCloudLine(".##................");
//		CloudLine cloudLine5 = TestUtils.stringToCloudLine("...................");
//
//		cloudLine1.setTime(format.parse("04/02/2015 13:15"));
//		cloudLine2.setTime(format.parse("04/02/2015 13:25"));
//		cloudLine3.setTime(format.parse("04/02/2015 13:35"));
//		cloudLine4.setTime(format.parse("04/02/2015 13:45"));
//		cloudLine5.setTime(format.parse("04/02/2015 13:55"));
//
//		cloudLines.add(cloudLine1);
//		cloudLines.add(cloudLine2);
//		cloudLines.add(cloudLine3);
//		cloudLines.add(cloudLine4);
//		cloudLines.add(cloudLine5);
//
//		AproximationResult aproximationResult = new RainAproximator(configuration).aproximate(cloudLines);
//		assertNotNull(aproximationResult);
//
//		AproximationResultType actualType = aproximationResult.getType();
//		AproximationResultType expectedType = AproximationResultType.RAIN_UNKNOWN;
//		Date actualTime = aproximationResult.getPredictTime();
//
//		assertNull(actualTime);
//		assertEquals(expectedType, actualType);
	}
	
	@Test
	public void testAproximateSunUnknown() throws ParseException {
//		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
//		CloudLine cloudLine1 = TestUtils.stringToCloudLine("#######..##########");
//		CloudLine cloudLine2 = TestUtils.stringToCloudLine("###################");
//		CloudLine cloudLine3 = TestUtils.stringToCloudLine(".........#.........");
//		CloudLine cloudLine4 = TestUtils.stringToCloudLine("###################");
//		CloudLine cloudLine5 = TestUtils.stringToCloudLine("#####.#########..##");
//
//		cloudLine1.setTime(format.parse("04/02/2015 13:15"));
//		cloudLine2.setTime(format.parse("04/02/2015 13:25"));
//		cloudLine3.setTime(format.parse("04/02/2015 13:35"));
//		cloudLine4.setTime(format.parse("04/02/2015 13:45"));
//		cloudLine5.setTime(format.parse("04/02/2015 13:55"));
//
//		cloudLines.add(cloudLine1);
//		cloudLines.add(cloudLine2);
//		cloudLines.add(cloudLine3);
//		cloudLines.add(cloudLine4);
//		cloudLines.add(cloudLine5);
//
//		AproximationResult aproximationResult = new RainAproximator(configuration).aproximate(cloudLines);
//		assertNotNull(aproximationResult);
//
//		AproximationResultType actualType = aproximationResult.getType();
//		AproximationResultType expectedType = AproximationResultType.SUN_UNKNOWN;
//		Date actualTime = aproximationResult.getPredictTime();
//
//		assertNull(actualTime);
//		assertEquals(expectedType, actualType);
	}
	
	@Test
	public void testAproximateSunUnknownDateForPast() throws ParseException {
//		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
//		CloudLine cloudLine1 = TestUtils.stringToCloudLine("######.............");
//		CloudLine cloudLine2 = TestUtils.stringToCloudLine("#########..........");
//		CloudLine cloudLine3 = TestUtils.stringToCloudLine("############.......");
//		CloudLine cloudLine4 = TestUtils.stringToCloudLine("###############....");
//		CloudLine cloudLine5 = TestUtils.stringToCloudLine("#####.###########..");
//
//		cloudLine1.setTime(this.format.parse("04/02/2015 13:15"));
//		cloudLine2.setTime(this.format.parse("04/02/2015 13:25"));
//		cloudLine3.setTime(this.format.parse("04/02/2015 13:35"));
//		cloudLine4.setTime(this.format.parse("04/02/2015 13:45"));
//		cloudLine5.setTime(this.format.parse("04/02/2015 13:55"));
//
//
//		cloudLines.add(cloudLine1);
//		cloudLines.add(cloudLine2);
//		cloudLines.add(cloudLine3);
//		cloudLines.add(cloudLine4);
//		cloudLines.add(cloudLine5);
//
//		AproximationResult aproximationResult = new RainAproximator(configuration).aproximate(cloudLines);
//		assertNotNull(aproximationResult);
//
//		AproximationResultType actualType = aproximationResult.getType();
//		AproximationResultType expectedType = AproximationResultType.SUN_UNSURE;
//		Date actualTime = aproximationResult.getPredictTime();
//
//		assertNull(actualTime);
//		assertEquals(expectedType, actualType);
	}
	
}
