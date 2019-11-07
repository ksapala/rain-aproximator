package org.ksapala.rainaproximator.aproximation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.utils.TimeFactory;
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
import java.util.Optional;

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
    private TimeFactory timeFactory;

    private TestUtils testUtils;


    @Before
	public void setUp() {
        testUtils = new TestUtils(configuration);
        LocalDateTime fixedTime = TestUtils.time("04-02-2115 14:00");
        doReturn(fixedTime).when(timeFactory).now();

	}

	@Test
	public void testCloudFromString() {
		String stringCloud = "....#.....####....";
        Cloud cloud = testUtils.stringToCloud(stringCloud);

		assertEquals(stringCloud, cloud.getLineAsString());
	}
	
	@Test
	public void testAproximateRainAtTime() {
		List<Cloud> clouds = new ArrayList<>();
		Cloud cloud1 = testUtils.stringToCloud("............###########...");
		Cloud cloud2 = testUtils.stringToCloud("..........###########.....");
		Cloud cloud3 = testUtils.stringToCloud("........###########.......");
		Cloud cloud4 = testUtils.stringToCloud("......###########.........");
		Cloud cloud5 = testUtils.stringToCloud("....###########...........");

		cloud1.setTime(TestUtils.time("04-02-2115 13:15"));
		cloud2.setTime(TestUtils.time("04-02-2115 13:25"));
		cloud3.setTime(TestUtils.time("04-02-2115 13:35"));
		cloud4.setTime(TestUtils.time("04-02-2115 13:45"));
		cloud5.setTime(TestUtils.time("04-02-2115 13:55"));
		LocalDateTime expectedTime = TestUtils.time("04-02-2115 14:15");

		clouds.add(cloud1);
		clouds.add(cloud2);
		clouds.add(cloud3);
		clouds.add(cloud4);
		clouds.add(cloud5);

		AproximationResult aproximationResult = rainAproximator.aproximate(clouds, 0).getAproximationResult();
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.RAIN_AT_TIME;
		Optional<LocalDateTime> actualTime = aproximationResult.getTime();

		assertEquals(expectedType, actualType);
		assertEquals(expectedTime, actualTime.get());
		
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

		cloud1.setTime(TestUtils.time("04-02-2115 13:15"));
		cloud2.setTime(TestUtils.time("04-02-2115 13:25"));
		cloud3.setTime(TestUtils.time("04-02-2115 13:35"));
		cloud4.setTime(TestUtils.time("04-02-2115 13:45"));
		cloud5.setTime(TestUtils.time("04-02-2115 13:55"));
        LocalDateTime expectedTime = TestUtils.time("04-02-2115 14:10");

		clouds.add(cloud1);
		clouds.add(cloud2);
		clouds.add(cloud3);
		clouds.add(cloud4);
		clouds.add(cloud5);

		// when
        AproximationResult aproximationResult = rainAproximator.aproximate(clouds, 0).getAproximationResult();

		// then
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.SUN_AT_TIME;
        Optional<LocalDateTime> actualTime = aproximationResult.getTime();

		assertEquals(expectedType, actualType);
		assertEquals(expectedTime, actualTime.get());
	}
	
	@Test
	public void testAproximateRainUnknown() {
		List<Cloud> clouds = new ArrayList<>();
		Cloud cloud1 = testUtils.stringToCloud("...................");
		Cloud cloud2 = testUtils.stringToCloud("....##########.....");
		Cloud cloud3 = testUtils.stringToCloud("...................");
		Cloud cloud4 = testUtils.stringToCloud("...................");
		Cloud cloud5 = testUtils.stringToCloud("...................");

		cloud1.setTime(TestUtils.time("04-02-2115 13:15"));
		cloud2.setTime(TestUtils.time("04-02-2115 13:25"));
		cloud3.setTime(TestUtils.time("04-02-2115 13:35"));
		cloud4.setTime(TestUtils.time("04-02-2115 13:45"));
		cloud5.setTime(TestUtils.time("04-02-2115 13:55"));

		clouds.add(cloud1);
		clouds.add(cloud2);
		clouds.add(cloud3);
		clouds.add(cloud4);
		clouds.add(cloud5);

        AproximationResult aproximationResult = rainAproximator.aproximate(clouds, 0).getAproximationResult();
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.RAIN_UNKNOWN;
		Optional<LocalDateTime> actualTime = aproximationResult.getTime();

		assertFalse(actualTime.isPresent());
		assertEquals(expectedType, actualType);
	}
	
	@Test
	public void testAproximateSunUnknown() {
		List<Cloud> clouds = new ArrayList<>();
		Cloud cloud1 = testUtils.stringToCloud("###################");
		Cloud cloud2 = testUtils.stringToCloud("###################");
		Cloud cloud3 = testUtils.stringToCloud("...................");
		Cloud cloud4 = testUtils.stringToCloud("###################");
		Cloud cloud5 = testUtils.stringToCloud("###################");

		cloud1.setTime(TestUtils.time("04-02-2115 13:15"));
		cloud2.setTime(TestUtils.time("04-02-2115 13:25"));
		cloud3.setTime(TestUtils.time("04-02-2115 13:35"));
		cloud4.setTime(TestUtils.time("04-02-2115 13:45"));
		cloud5.setTime(TestUtils.time("04-02-2115 13:55"));

		clouds.add(cloud1);
		clouds.add(cloud2);
		clouds.add(cloud3);
		clouds.add(cloud4);
		clouds.add(cloud5);

        AproximationResult aproximationResult = rainAproximator.aproximate(clouds, 0).getAproximationResult();
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.SUN_UNKNOWN;
        Optional<LocalDateTime> actualTime = aproximationResult.getTime();

		assertFalse(actualTime.isPresent());
		assertEquals(expectedType, actualType);
	}
	
	@Test
	public void testAproximateSunUnknownDateForPast() {
		List<Cloud> clouds = new ArrayList<>();
		Cloud cloud1 = testUtils.stringToCloud("######.............");
		Cloud cloud2 = testUtils.stringToCloud("#########..........");
		Cloud cloud3 = testUtils.stringToCloud("############.......");
		Cloud cloud4 = testUtils.stringToCloud("###############....");
		Cloud cloud5 = testUtils.stringToCloud("#################..");

		cloud1.setTime(TestUtils.time("04-02-2115 13:15"));
		cloud2.setTime(TestUtils.time("04-02-2115 13:25"));
		cloud3.setTime(TestUtils.time("04-02-2115 13:35"));
		cloud4.setTime(TestUtils.time("04-02-2115 13:45"));
		cloud5.setTime(TestUtils.time("04-02-2115 13:55"));


		clouds.add(cloud1);
		clouds.add(cloud2);
		clouds.add(cloud3);
		clouds.add(cloud4);
		clouds.add(cloud5);

        AproximationResult aproximationResult = rainAproximator.aproximate(clouds, 0).getAproximationResult();
		assertNotNull(aproximationResult);

		AproximationResultType actualType = aproximationResult.getType();
		AproximationResultType expectedType = AproximationResultType.SUN_GOES_AWAY;
        Optional<LocalDateTime> actualTime = aproximationResult.getTime();

		assertFalse(actualTime.isPresent());
		assertEquals(expectedType, actualType);
	}
	
}
