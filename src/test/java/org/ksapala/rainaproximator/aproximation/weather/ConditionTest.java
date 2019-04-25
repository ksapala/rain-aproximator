package org.ksapala.rainaproximator.aproximation.weather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.aproximation.regression.RegressionTimeFactory;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConditionTest {

    @Autowired
    private Configuration configuration;

    @SpyBean
    private RegressionTimeFactory regressionTimeFactory;

    private TestUtils testUtils;

    @Before
    public void setUp() {
        testUtils = new TestUtils(configuration);
        LocalDateTime fixedTime = testUtils.parseInTest("04-02-2115 14:35");
        doReturn(fixedTime).when(regressionTimeFactory).now();

    }

    @Test
    public void getRegressionSimple() {
        List<Cloud> clouds = new ArrayList<>();
        clouds.add(testUtils.cloud("..........###########.........", "04-02-2115 14:00"));
        clouds.add(testUtils.cloud("........###########...........", "04-02-2115 14:10"));
        clouds.add(testUtils.cloud("......###########.............", "04-02-2115 14:20"));
        clouds.add(testUtils.cloud("....###########...............", "04-02-2115 14:30"));


        Rain rain = new Rain(clouds, regressionTimeFactory, configuration.getAlgorithm());
        LocalDateTime expectedTime = testUtils.parseInTest("04-02-2115 14:50");

        assertEquals(expectedTime, TimeUtils.millisToDate((long) rain.getRegression()));
        assertFalse(rain.isRegressionNan());
        assertFalse(rain.isRegressionForPast());
        assertTrue(rain.isApproaching());
    }

    @Test
    public void getRegressionSecondRain() {
        List<Cloud> clouds = new ArrayList<>();
        clouds.add(testUtils.cloud("###.....#########.........", "04-02-2115 13:00"));
        clouds.add(testUtils.cloud("##....#########...........", "04-02-2115 13:10"));
        clouds.add(testUtils.cloud("#...#########.............", "04-02-2115 13:20"));
        clouds.add(testUtils.cloud("..#########...............", "04-02-2115 13:30"));
        clouds.add(testUtils.cloud("#########.................", "04-02-2115 13:40"));
        clouds.add(testUtils.cloud("#######...............####", "04-02-2115 13:50"));
        clouds.add(testUtils.cloud("#####.............########", "04-02-2115 14:00"));
        clouds.add(testUtils.cloud("###...........############", "04-02-2115 14:10"));
        clouds.add(testUtils.cloud("..........################", "04-02-2115 14:20"));
        clouds.add(testUtils.cloud("......####################", "04-02-2115 14:30"));


        Rain rain = new Rain(clouds, regressionTimeFactory, configuration.getAlgorithm());
        LocalDateTime expectedTime = testUtils.parseInTest("04-02-2115 14:45");

        assertEquals(expectedTime, TimeUtils.millisToDate((long) rain.getRegression()));
        assertFalse(rain.isRegressionNan());
        assertFalse(rain.isRegressionForPast());
        assertTrue(rain.isApproaching());
    }

    @Test
    public void getRegressionSecondLongRain() {
        List<Cloud> clouds = new ArrayList<>();
        clouds.add(testUtils.cloud("###.....#########.........", "04-02-2115 13:00"));
        clouds.add(testUtils.cloud("##....#########.......####", "04-02-2115 13:10"));
        clouds.add(testUtils.cloud("#...#########.......######", "04-02-2115 13:20"));
        clouds.add(testUtils.cloud("..#########.......########", "04-02-2115 13:30"));
        clouds.add(testUtils.cloud("#########.......##########", "04-02-2115 13:40"));
        clouds.add(testUtils.cloud("#######.......############", "04-02-2115 13:50"));
        clouds.add(testUtils.cloud("#####.......##############", "04-02-2115 14:00"));
        clouds.add(testUtils.cloud("###.......################", "04-02-2115 14:10"));
        clouds.add(testUtils.cloud("........##################", "04-02-2115 14:20"));
        clouds.add(testUtils.cloud("......####################", "04-02-2115 14:30"));


        Rain rain = new Rain(clouds, regressionTimeFactory, configuration.getAlgorithm());
        LocalDateTime expectedTime = testUtils.parseInTest("04-02-2115 15:00");

        assertEquals(expectedTime, TimeUtils.millisToDate((long) rain.getRegression()));
        assertFalse(rain.isRegressionNan());
        assertFalse(rain.isRegressionForPast());
        assertTrue(rain.isApproaching());
    }

    @Test
    public void getRegressionSecondLongRainButFutureIsZero() {
        List<Cloud> clouds = new ArrayList<>();
        clouds.add(testUtils.cloud("###.....##########........", "04-02-2115 13:00"));
        clouds.add(testUtils.cloud("##....#########...........", "04-02-2115 13:10"));
        clouds.add(testUtils.cloud("#...########..............", "04-02-2115 13:20"));
        clouds.add(testUtils.cloud("..#######.................", "04-02-2115 13:30"));
        clouds.add(testUtils.cloud("######....................", "04-02-2115 13:40"));
        clouds.add(testUtils.cloud("......................####", "04-02-2115 13:50"));
        clouds.add(testUtils.cloud("..................########", "04-02-2115 14:00"));
        clouds.add(testUtils.cloud("..............############", "04-02-2115 14:10"));
        clouds.add(testUtils.cloud("..........################", "04-02-2115 14:20"));
        clouds.add(testUtils.cloud("......####################", "04-02-2115 14:30"));


        Rain rain = new Rain(clouds, regressionTimeFactory, configuration.getAlgorithm());
        LocalDateTime expectedTime = testUtils.parseInTest("04-02-2115 14:45");

        assertEquals(expectedTime, TimeUtils.millisToDate((long) rain.getRegression()));
        assertFalse(rain.isRegressionNan());
        assertFalse(rain.isRegressionForPast());
        assertTrue(rain.isApproaching());
    }

    @Test
    public void getRegressionRainInTheMeantime() {
        List<Cloud> clouds = new ArrayList<>();
        clouds.add(testUtils.cloud("................###########.....", "04-02-2115 14:00"));
        clouds.add(testUtils.cloud("...........###########..........", "04-02-2115 14:10"));
        clouds.add(testUtils.cloud("......###########...............", "04-02-2115 14:20"));
        clouds.add(testUtils.cloud(".###########....................", "04-02-2115 14:30"));


        Rain rain = new Rain(clouds, regressionTimeFactory, configuration.getAlgorithm());
        LocalDateTime expectedTime = testUtils.parseInTest("04-02-2115 14:32");

        assertEquals(expectedTime, TimeUtils.millisToDate((long) rain.getRegression()));
        assertFalse(rain.isRegressionNan());
        assertTrue(rain.isRegressionForPast());
        assertTrue(rain.isApproaching());
    }

}