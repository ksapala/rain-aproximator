package org.ksapala.rainaproximator.aproximation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.BaseTest;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.settings.Settings;
import org.ksapala.rainaproximator.settings.developer.DeveloperSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RainAproximatorTest extends BaseTest {

	@Autowired
	private Configuration configuration;

	@Before
	public void setUp() {
		super.setUp();
		Settings.injectableSettings = new DeveloperSettings();
	}

	@Test
	public void testCloudLineFromString() {
		String stringCloudLine = "....#.....####....";
		CloudLine cloudLine = CloudLine.fromString(stringCloudLine);
		
		assertEquals(stringCloudLine, cloudLine.getLineAsString());
	}
	
	@Test
	public void testAproximateRainAtTime() throws ParseException {
		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
		CloudLine cloudLine1 = CloudLine.fromString("....#.......######...");
		CloudLine cloudLine2 = CloudLine.fromString("....#.....######.....");
		CloudLine cloudLine3 = CloudLine.fromString("........######.......");
		CloudLine cloudLine4 = CloudLine.fromString("......####.#.........");
		CloudLine cloudLine5 = CloudLine.fromString("....####.#...........");
		

		
		cloudLine1.setDate(this.format.parse("04/02/2015 13:15"));
		cloudLine2.setDate(this.format.parse("04/02/2015 13:25"));
		cloudLine3.setDate(this.format.parse("04/02/2015 13:35"));
		cloudLine4.setDate(this.format.parse("04/02/2015 13:45"));
		cloudLine5.setDate(this.format.parse("04/02/2015 13:55"));
		Date expectedTime = this.format.parse("04/02/2015 14:15");
		
		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);
		
		AproximationResult aproximationResult = new RainAproximator(configuration).aproximate(cloudLines);
		assertNotNull(aproximationResult);
		
		AproximationResultType actualType = aproximationResult.getType(); 
		AproximationResultType expectedType = AproximationResultType.RAIN_AT_TIME;
		Date actualTime = aproximationResult.getPredictTime();
		
		assertEquals(expectedType, actualType);
		assertEquals(expectedTime, actualTime);
		
	}
	
	@Test
	public void testAproximateSunAtTime() throws ParseException {
		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
		CloudLine cloudLine1 = CloudLine.fromString("######################.");
		CloudLine cloudLine2 = CloudLine.fromString("##################.....");
		CloudLine cloudLine3 = CloudLine.fromString("##############.........");
		CloudLine cloudLine4 = CloudLine.fromString("##########.............");
		CloudLine cloudLine5 = CloudLine.fromString("######.................");
		
		cloudLine1.setDate(this.format.parse("04/02/2015 13:15"));
		cloudLine2.setDate(this.format.parse("04/02/2015 13:25"));
		cloudLine3.setDate(this.format.parse("04/02/2015 13:35"));
		cloudLine4.setDate(this.format.parse("04/02/2015 13:45"));
		cloudLine5.setDate(this.format.parse("04/02/2015 13:55"));
		Date expectedTime = this.format.parse("04/02/2015 14:10");
		
		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);
		
		AproximationResult aproximationResult = new RainAproximator(configuration).aproximate(cloudLines);
		assertNotNull(aproximationResult);
		
		AproximationResultType actualType = aproximationResult.getType(); 
		AproximationResultType expectedType = AproximationResultType.SUN_AT_TIME;
		Date actualTime = aproximationResult.getPredictTime();
		
		assertEquals(expectedType, actualType);
		assertEquals(expectedTime, actualTime);
	}
	
	@Test
	public void testAproximateRainUnknown() throws ParseException {
		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
		CloudLine cloudLine1 = CloudLine.fromString("...................");
		CloudLine cloudLine2 = CloudLine.fromString("....##########.....");
		CloudLine cloudLine3 = CloudLine.fromString(".........#.........");
		CloudLine cloudLine4 = CloudLine.fromString(".##................");
		CloudLine cloudLine5 = CloudLine.fromString("...................");
		
		cloudLine1.setDate(format.parse("04/02/2015 13:15"));
		cloudLine2.setDate(format.parse("04/02/2015 13:25"));
		cloudLine3.setDate(format.parse("04/02/2015 13:35"));
		cloudLine4.setDate(format.parse("04/02/2015 13:45"));
		cloudLine5.setDate(format.parse("04/02/2015 13:55"));
		
		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);
		
		AproximationResult aproximationResult = new RainAproximator(configuration).aproximate(cloudLines);
		assertNotNull(aproximationResult);
		
		AproximationResultType actualType = aproximationResult.getType(); 
		AproximationResultType expectedType = AproximationResultType.RAIN_UNKNOWN;
		Date actualTime = aproximationResult.getPredictTime();
		
		assertNull(actualTime);
		assertEquals(expectedType, actualType);
	}
	
	@Test
	public void testAproximateSunUnknown() throws ParseException {
		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
		CloudLine cloudLine1 = CloudLine.fromString("#######..##########");
		CloudLine cloudLine2 = CloudLine.fromString("###################");
		CloudLine cloudLine3 = CloudLine.fromString(".........#.........");
		CloudLine cloudLine4 = CloudLine.fromString("###################");
		CloudLine cloudLine5 = CloudLine.fromString("#####.#########..##");
		
		cloudLine1.setDate(format.parse("04/02/2015 13:15"));
		cloudLine2.setDate(format.parse("04/02/2015 13:25"));
		cloudLine3.setDate(format.parse("04/02/2015 13:35"));
		cloudLine4.setDate(format.parse("04/02/2015 13:45"));
		cloudLine5.setDate(format.parse("04/02/2015 13:55"));
		
		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);
		
		AproximationResult aproximationResult = new RainAproximator(configuration).aproximate(cloudLines);
		assertNotNull(aproximationResult);
		
		AproximationResultType actualType = aproximationResult.getType(); 
		AproximationResultType expectedType = AproximationResultType.SUN_UNKNOWN;
		Date actualTime = aproximationResult.getPredictTime();
		
		assertNull(actualTime);
		assertEquals(expectedType, actualType);
	}
	
	@Test
	public void testAproximateSunUnknownDateForPast() throws ParseException {
		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
		CloudLine cloudLine1 = CloudLine.fromString("######.............");
		CloudLine cloudLine2 = CloudLine.fromString("#########..........");
		CloudLine cloudLine3 = CloudLine.fromString("############.......");
		CloudLine cloudLine4 = CloudLine.fromString("###############....");
		CloudLine cloudLine5 = CloudLine.fromString("#####.###########..");
		
		cloudLine1.setDate(this.format.parse("04/02/2015 13:15"));
		cloudLine2.setDate(this.format.parse("04/02/2015 13:25"));
		cloudLine3.setDate(this.format.parse("04/02/2015 13:35"));
		cloudLine4.setDate(this.format.parse("04/02/2015 13:45"));
		cloudLine5.setDate(this.format.parse("04/02/2015 13:55"));
		
		
		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);
		
		AproximationResult aproximationResult = new RainAproximator(configuration).aproximate(cloudLines);
		assertNotNull(aproximationResult);
		
		AproximationResultType actualType = aproximationResult.getType(); 
		AproximationResultType expectedType = AproximationResultType.SUN_UNSURE;
		Date actualTime = aproximationResult.getPredictTime();
		
		assertNull(actualTime);
		assertEquals(expectedType, actualType);
	}
	
}
