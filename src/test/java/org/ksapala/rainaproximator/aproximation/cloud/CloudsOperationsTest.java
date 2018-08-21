package org.ksapala.rainaproximator.aproximation.cloud;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudsOperationsTest {

    @Autowired
    private Configuration configuration;


    private TestUtils testUtils;

    @Before
    public void setUp() {
        testUtils = new TestUtils(configuration);
    }

    @Test
    public void filterForRainRegression() {
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

        List<CloudLine> forRainRegression = CloudsOperations.filterForRainRegression(cloudLines);

        assertEquals(5, forRainRegression.size());
        CloudLine first = forRainRegression.get(0);
        CloudLine last = forRainRegression.get(4);

        assertEquals(testUtils.cloudLine("#######...............####", "04/02/2115 13:50"), first);
        assertEquals(testUtils.cloudLine("......####################", "04/02/2115 14:30"), last);

    }

    @Test
    public void filterForRainRegressionSecondRain() {
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

        List<CloudLine> forRainRegression = CloudsOperations.filterForRainRegression(cloudLines);

        assertEquals(6, forRainRegression.size());
        CloudLine first = forRainRegression.get(0);
        CloudLine last = forRainRegression.get(5);

        assertEquals(testUtils.cloudLine("#########.......##########", "04/02/2115 13:40"), first);
        assertEquals(testUtils.cloudLine("......####################", "04/02/2115 14:30"), last);
    }

    @Test
    public void filterForSunRegressionSecondRain() {
        List<CloudLine> cloudLines = new ArrayList<>();
        cloudLines.add(testUtils.cloudLine("...####...#########.....", "04/02/2115 13:00"));
        cloudLines.add(testUtils.cloudLine("##....#########.......##", "04/02/2115 13:10"));
        cloudLines.add(testUtils.cloudLine("#...#########.......####", "04/02/2115 13:20"));
        cloudLines.add(testUtils.cloudLine("..#########.......######", "04/02/2115 13:30"));
        cloudLines.add(testUtils.cloudLine("#######.......#########.", "04/02/2115 13:40"));
        cloudLines.add(testUtils.cloudLine("....################....", "04/02/2115 13:50"));
        cloudLines.add(testUtils.cloudLine("################........", "04/02/2115 14:00"));
        cloudLines.add(testUtils.cloudLine("############............", "04/02/2115 14:10"));
        cloudLines.add(testUtils.cloudLine("########................", "04/02/2115 14:20"));
        cloudLines.add(testUtils.cloudLine("####....................", "04/02/2115 14:30"));

        List<CloudLine> forSunRegression = CloudsOperations.filterForSunRegression(cloudLines);

        assertEquals(5, forSunRegression.size());
        CloudLine first = forSunRegression.get(0);
        CloudLine last = forSunRegression.get(4);

        assertEquals(testUtils.cloudLine("....################....", "04/02/2115 13:50"), first);
        assertEquals(testUtils.cloudLine("####....................", "04/02/2115 14:30"), last);
    }
}