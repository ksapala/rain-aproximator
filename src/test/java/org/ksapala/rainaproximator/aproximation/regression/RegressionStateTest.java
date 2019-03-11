package org.ksapala.rainaproximator.aproximation.regression;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
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
public class RegressionStateTest {

    @Autowired
    private Configuration configuration;

    @SpyBean
    private RegressionTimeFactory regressionTimeFactory;

    private TestUtils testUtils;

    @Before
    public void setUp() {
        testUtils = new TestUtils(configuration);
        LocalDateTime fixedTime = testUtils.parseInTest("04/02/2115 14:35");
        doReturn(fixedTime).when(regressionTimeFactory).now();

    }

    @Test
    public void getRainRegressionSimple() {
        List<CloudLine> cloudLines = new ArrayList<>();
        cloudLines.add(testUtils.cloudLine("..........###########.........", "04/02/2115 14:00"));
        cloudLines.add(testUtils.cloudLine("........###########...........", "04/02/2115 14:10"));
        cloudLines.add(testUtils.cloudLine("......###########.............", "04/02/2115 14:20"));
        cloudLines.add(testUtils.cloudLine("....###########...............", "04/02/2115 14:30"));


        RegressionState regressionState = new RegressionState(cloudLines, regressionTimeFactory);
        LocalDateTime expectedTime = testUtils.parseInTest("04/02/2115 14:50");

        assertTrue(regressionState.isSun());
        assertEquals(expectedTime, TimeUtils.millisToLocalDateAndTime((long) regressionState.getRainRegression()));
        assertFalse(regressionState.isRainRegressionNan());
        assertFalse(regressionState.isRainRegressionForPast());
        assertTrue(regressionState.rainIsApproaching());
    }

    @Test
    public void getRainRegressionSecondRain() {
        List<CloudLine> cloudLines = new ArrayList<>();
        cloudLines.add(testUtils.cloudLine("###.....#########.........", "04/02/2115 13:00"));
        cloudLines.add(testUtils.cloudLine("##....#########...........", "04/02/2115 13:10"));
        cloudLines.add(testUtils.cloudLine("#...#########.............", "04/02/2115 13:20"));
        cloudLines.add(testUtils.cloudLine("..#########...............", "04/02/2115 13:30"));
        cloudLines.add(testUtils.cloudLine("#########.................", "04/02/2115 13:40"));
        cloudLines.add(testUtils.cloudLine("#######...............####", "04/02/2115 13:50"));
        cloudLines.add(testUtils.cloudLine("#####.............########", "04/02/2115 14:00"));
        cloudLines.add(testUtils.cloudLine("###...........############", "04/02/2115 14:10"));
        cloudLines.add(testUtils.cloudLine("..........################", "04/02/2115 14:20"));
        cloudLines.add(testUtils.cloudLine("......####################", "04/02/2115 14:30"));


        RegressionState regressionState = new RegressionState(cloudLines, regressionTimeFactory);
        LocalDateTime expectedTime = testUtils.parseInTest("04/02/2115 14:45");

        assertTrue(regressionState.isSun());
        assertEquals(expectedTime, TimeUtils.millisToLocalDateAndTime((long) regressionState.getRainRegression()));
        assertFalse(regressionState.isRainRegressionNan());
        assertFalse(regressionState.isRainRegressionForPast());
        assertTrue(regressionState.rainIsApproaching());
    }

    @Test
    public void getRainRegressionSecondLongRain() {
        List<CloudLine> cloudLines = new ArrayList<>();
        cloudLines.add(testUtils.cloudLine("###.....#########.........", "04/02/2115 13:00"));
        cloudLines.add(testUtils.cloudLine("##....#########.......####", "04/02/2115 13:10"));
        cloudLines.add(testUtils.cloudLine("#...#########.......######", "04/02/2115 13:20"));
        cloudLines.add(testUtils.cloudLine("..#########.......########", "04/02/2115 13:30"));
        cloudLines.add(testUtils.cloudLine("#########.......##########", "04/02/2115 13:40"));
        cloudLines.add(testUtils.cloudLine("#######.......############", "04/02/2115 13:50"));
        cloudLines.add(testUtils.cloudLine("#####.......##############", "04/02/2115 14:00"));
        cloudLines.add(testUtils.cloudLine("###.......################", "04/02/2115 14:10"));
        cloudLines.add(testUtils.cloudLine("........##################", "04/02/2115 14:20"));
        cloudLines.add(testUtils.cloudLine("......####################", "04/02/2115 14:30"));


        RegressionState regressionState = new RegressionState(cloudLines, regressionTimeFactory);
        LocalDateTime expectedTime = testUtils.parseInTest("04/02/2115 15:00");

        assertTrue(regressionState.isSun());
        assertEquals(expectedTime, TimeUtils.millisToLocalDateAndTime((long) regressionState.getRainRegression()));
        assertFalse(regressionState.isRainRegressionNan());
        assertFalse(regressionState.isRainRegressionForPast());
        assertTrue(regressionState.rainIsApproaching());
    }

    @Test
    public void getRainRegressionSecondLongRainButFutureIsZero() {
        List<CloudLine> cloudLines = new ArrayList<>();
        cloudLines.add(testUtils.cloudLine("###.....##########........", "04/02/2115 13:00"));
        cloudLines.add(testUtils.cloudLine("##....#########...........", "04/02/2115 13:10"));
        cloudLines.add(testUtils.cloudLine("#...########..............", "04/02/2115 13:20"));
        cloudLines.add(testUtils.cloudLine("..#######.................", "04/02/2115 13:30"));
        cloudLines.add(testUtils.cloudLine("######....................", "04/02/2115 13:40"));
        cloudLines.add(testUtils.cloudLine("......................####", "04/02/2115 13:50"));
        cloudLines.add(testUtils.cloudLine("..................########", "04/02/2115 14:00"));
        cloudLines.add(testUtils.cloudLine("..............############", "04/02/2115 14:10"));
        cloudLines.add(testUtils.cloudLine("..........################", "04/02/2115 14:20"));
        cloudLines.add(testUtils.cloudLine("......####################", "04/02/2115 14:30"));


        RegressionState regressionState = new RegressionState(cloudLines, regressionTimeFactory);
        LocalDateTime expectedTime = testUtils.parseInTest("04/02/2115 14:45");

        assertTrue(regressionState.isSun());
        assertEquals(expectedTime, TimeUtils.millisToLocalDateAndTime((long) regressionState.getRainRegression()));
        assertFalse(regressionState.isRainRegressionNan());
        assertFalse(regressionState.isRainRegressionForPast());
        assertTrue(regressionState.rainIsApproaching());
    }

    @Test
    public void getRainRegressionRainInTheMeantime() {
        List<CloudLine> cloudLines = new ArrayList<>();
        cloudLines.add(testUtils.cloudLine("................###########.....", "04/02/2115 14:00"));
        cloudLines.add(testUtils.cloudLine("...........###########..........", "04/02/2115 14:10"));
        cloudLines.add(testUtils.cloudLine("......###########...............", "04/02/2115 14:20"));
        cloudLines.add(testUtils.cloudLine(".###########....................", "04/02/2115 14:30"));


        RegressionState regressionState = new RegressionState(cloudLines, regressionTimeFactory);
        LocalDateTime expectedTime = testUtils.parseInTest("04/02/2115 14:32");

        assertFalse(regressionState.isSun());
        assertEquals(expectedTime, TimeUtils.millisToLocalDateAndTime((long) regressionState.getRainRegression()));
        assertFalse(regressionState.isRainRegressionNan());
        assertTrue(regressionState.isRainRegressionForPast());
        assertTrue(regressionState.rainIsApproaching());
    }

}