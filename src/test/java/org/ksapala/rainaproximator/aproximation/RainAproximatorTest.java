package org.ksapala.rainaproximator.aproximation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.aproximation.regression.RegressionTimeFactory;
import org.ksapala.rainaproximator.aproximation.result.AproximationResult;
import org.ksapala.rainaproximator.aproximation.result.AproximationResultType;
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
	public void testCloudFromString() {
		String stringCloud = "....#.....####....";
        Cloud cloud = testUtils.stringToCloud(stringCloud);

		assertEquals(stringCloud, cloud.getLineAsString());
	}
	
	@Test
	public void testAproximateRainAtTime() {
		List<Cloud> clouds = new ArrayList<Cloud>();
		Cloud cloud1 = testUtils.stringToCloud("............###########...");
		Cloud cloud2 = testUtils.stringToCloud("..........###########.....");
		Cloud cloud3 = testUtils.stringToCloud("........###########.......");
		Cloud cloud4 = testUtils.stringToCloud("......###########.........");
		Cloud cloud5 = testUtils.stringToCloud("....###########...........");

		cloud1.setTime(testUtils.parseInTest("04/02/2115 13:15"));
		cloud2.setTime(testUtils.parseInTest("04/02/2115 13:25"));
		cloud3.setTime(testUtils.parseInTest("04/02/2115 13:35"));
		cloud4.setTime(testUtils.parseInTest("04/02/2115 13:45"));
		cloud5.setTime(testUtils.parseInTest("04/02/2115 13:55"));
		LocalDateTime expectedTime = testUtils.parseInTest("04/02/2115 14:15");

		clouds.add(cloud1);
		clouds.add(cloud2);
		clouds.add(cloud3);
		clouds.add(cloud4);
		clouds.add(cloud5);

		AproximationResult aproximationResult = rainAproximator.aproximate(clouds);
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.RAIN_AT_TIME;
		LocalDateTime actualTime = aproximationResult.getPredictTime();

		assertEquals(expectedType, actualType);
		assertEquals(expectedTime, actualTime);
		
	}
	
	@Test
	public void testAproximateSunAtTime() {
        // given
		List<Cloud> clouds = new ArrayList<>();
		Cloud cloud1 = testUtils.stringToCloud("######################.");
		Cloud cloud2 = testUtils.stringToCloud("##################.....");
		Cloud cloud3 = testUtils.stringToCloud("##############.........");
		Cloud cloud4 = testUtils.stringToCloud("##########.............");
		Cloud cloud5 = testUtils.stringToCloud("######.................");

		cloud1.setTime(testUtils.parseInTest("04/02/2115 13:15"));
		cloud2.setTime(testUtils.parseInTest("04/02/2115 13:25"));
		cloud3.setTime(testUtils.parseInTest("04/02/2115 13:35"));
		cloud4.setTime(testUtils.parseInTest("04/02/2115 13:45"));
		cloud5.setTime(testUtils.parseInTest("04/02/2115 13:55"));
        LocalDateTime expectedTime = testUtils.parseInTest("04/02/2115 14:10");

		clouds.add(cloud1);
		clouds.add(cloud2);
		clouds.add(cloud3);
		clouds.add(cloud4);
		clouds.add(cloud5);

		// when
		AproximationResult aproximationResult = rainAproximator.aproximate(clouds);

		// then
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.SUN_AT_TIME;
        LocalDateTime actualTime = aproximationResult.getPredictTime();

		assertEquals(expectedType, actualType);
		assertEquals(expectedTime, actualTime);
	}
	
	@Test
	public void testAproximateRainUnknown() {
		List<Cloud> clouds = new ArrayList<>();
		Cloud cloud1 = testUtils.stringToCloud("...................");
		Cloud cloud2 = testUtils.stringToCloud("....##########.....");
		Cloud cloud3 = testUtils.stringToCloud("...................");
		Cloud cloud4 = testUtils.stringToCloud("...................");
		Cloud cloud5 = testUtils.stringToCloud("...................");

		cloud1.setTime(testUtils.parseInTest("04/02/2115 13:15"));
		cloud2.setTime(testUtils.parseInTest("04/02/2115 13:25"));
		cloud3.setTime(testUtils.parseInTest("04/02/2115 13:35"));
		cloud4.setTime(testUtils.parseInTest("04/02/2115 13:45"));
		cloud5.setTime(testUtils.parseInTest("04/02/2115 13:55"));

		clouds.add(cloud1);
		clouds.add(cloud2);
		clouds.add(cloud3);
		clouds.add(cloud4);
		clouds.add(cloud5);

		AproximationResult aproximationResult = rainAproximator.aproximate(clouds);
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.RAIN_UNKNOWN;
		LocalDateTime actualTime = aproximationResult.getPredictTime();

		assertNull(actualTime);
		assertEquals(expectedType, actualType);
	}
	
	@Test
	public void testAproximateSunUnknown() {
		List<Cloud> clouds = new ArrayList<Cloud>();
		Cloud cloud1 = testUtils.stringToCloud("###################");
		Cloud cloud2 = testUtils.stringToCloud("###################");
		Cloud cloud3 = testUtils.stringToCloud("...................");
		Cloud cloud4 = testUtils.stringToCloud("###################");
		Cloud cloud5 = testUtils.stringToCloud("###################");

		cloud1.setTime(testUtils.parseInTest("04/02/2115 13:15"));
		cloud2.setTime(testUtils.parseInTest("04/02/2115 13:25"));
		cloud3.setTime(testUtils.parseInTest("04/02/2115 13:35"));
		cloud4.setTime(testUtils.parseInTest("04/02/2115 13:45"));
		cloud5.setTime(testUtils.parseInTest("04/02/2115 13:55"));

		clouds.add(cloud1);
		clouds.add(cloud2);
		clouds.add(cloud3);
		clouds.add(cloud4);
		clouds.add(cloud5);

		AproximationResult aproximationResult = rainAproximator.aproximate(clouds);
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.SUN_UNKNOWN;
        LocalDateTime actualTime = aproximationResult.getPredictTime();

		assertNull(actualTime);
		assertEquals(expectedType, actualType);
	}
	
	@Test
	public void testAproximateSunUnknownDateForPast() {
		List<Cloud> clouds = new ArrayList<Cloud>();
		Cloud cloud1 = testUtils.stringToCloud("######.............");
		Cloud cloud2 = testUtils.stringToCloud("#########..........");
		Cloud cloud3 = testUtils.stringToCloud("############.......");
		Cloud cloud4 = testUtils.stringToCloud("###############....");
		Cloud cloud5 = testUtils.stringToCloud("#################..");

		cloud1.setTime(testUtils.parseInTest("04/02/2115 13:15"));
		cloud2.setTime(testUtils.parseInTest("04/02/2115 13:25"));
		cloud3.setTime(testUtils.parseInTest("04/02/2115 13:35"));
		cloud4.setTime(testUtils.parseInTest("04/02/2115 13:45"));
		cloud5.setTime(testUtils.parseInTest("04/02/2115 13:55"));


		clouds.add(cloud1);
		clouds.add(cloud2);
		clouds.add(cloud3);
		clouds.add(cloud4);
		clouds.add(cloud5);

		AproximationResult aproximationResult = rainAproximator.aproximate(clouds);
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.SUN_UNSURE;
        LocalDateTime actualTime = aproximationResult.getPredictTime();

		assertNull(actualTime);
		assertEquals(expectedType, actualType);
	}
	
}
