package org.ksapala.rainaproximator.aproximation.cloud;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.domainfilters.Filters;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudsOperationsTest {

    @Autowired
    private Configuration configuration;


    private TestUtils testUtils;
    private Filters filters;

    @Before
    public void setUp() {
        testUtils = new TestUtils(configuration);
        filters = new Filters(configuration.getAlgorithm());
    }

    @Test
    public void filterForRainRegression() {
        List<Cloud> cloudLines = new ArrayList<>();
        cloudLines.add(testUtils.cloud("###.....#########.........", "04/02/2115 13:00"));
        cloudLines.add(testUtils.cloud("##....#########...........", "04/02/2115 13:10"));
        cloudLines.add(testUtils.cloud("#...#########.............", "04/02/2115 13:20"));
        cloudLines.add(testUtils.cloud("..#########...............", "04/02/2115 13:30"));
        cloudLines.add(testUtils.cloud("#########.................", "04/02/2115 13:40"));
        cloudLines.add(testUtils.cloud("#######...............####", "04/02/2115 13:50"));
        cloudLines.add(testUtils.cloud("#####.............########", "04/02/2115 14:00"));
        cloudLines.add(testUtils.cloud("###...........############", "04/02/2115 14:10"));
        cloudLines.add(testUtils.cloud("..........################", "04/02/2115 14:20"));
        cloudLines.add(testUtils.cloud("......####################", "04/02/2115 14:30"));

        List<Cloud> forRainRegression = filters.filterRainCandidates(cloudLines);

        assertEquals(5, forRainRegression.size());
        Cloud first = forRainRegression.get(0);
        Cloud last = forRainRegression.get(4);

        assertEquals(testUtils.cloud("#######...............####", "04/02/2115 13:50"), first);
        assertEquals(testUtils.cloud("......####################", "04/02/2115 14:30"), last);

    }

    @Test
    public void filterForRainRegressionSecondRain() {
        List<Cloud> cloudLines = new ArrayList<>();
        cloudLines.add(testUtils.cloud("###.....#########.........", "04/02/2115 13:00"));
        cloudLines.add(testUtils.cloud("##....#########.......####", "04/02/2115 13:10"));
        cloudLines.add(testUtils.cloud("#...#########.......######", "04/02/2115 13:20"));
        cloudLines.add(testUtils.cloud("..#########.......########", "04/02/2115 13:30"));
        cloudLines.add(testUtils.cloud("#########.......##########", "04/02/2115 13:40"));
        cloudLines.add(testUtils.cloud("#######.......############", "04/02/2115 13:50"));
        cloudLines.add(testUtils.cloud("#####.......##############", "04/02/2115 14:00"));
        cloudLines.add(testUtils.cloud("###.......################", "04/02/2115 14:10"));
        cloudLines.add(testUtils.cloud("........##################", "04/02/2115 14:20"));
        cloudLines.add(testUtils.cloud("......####################", "04/02/2115 14:30"));

        List<Cloud> forRainRegression = filters.filterRainCandidates(cloudLines);

        assertEquals(6, forRainRegression.size());
        Cloud first = forRainRegression.get(0);
        Cloud last = forRainRegression.get(5);

        assertEquals(testUtils.cloud("#########.......##########", "04/02/2115 13:40"), first);
        assertEquals(testUtils.cloud("......####################", "04/02/2115 14:30"), last);
    }

    @Test
    public void filterForSunRegressionSecondRain() {
        List<Cloud> cloudLines = new ArrayList<>();
        cloudLines.add(testUtils.cloud("...####...#########.....", "04/02/2115 13:00"));
        cloudLines.add(testUtils.cloud("##....#########.......##", "04/02/2115 13:10"));
        cloudLines.add(testUtils.cloud("#...#########.......####", "04/02/2115 13:20"));
        cloudLines.add(testUtils.cloud("..#########.......######", "04/02/2115 13:30"));
        cloudLines.add(testUtils.cloud("#######.......#########.", "04/02/2115 13:40"));
        cloudLines.add(testUtils.cloud("....################....", "04/02/2115 13:50"));
        cloudLines.add(testUtils.cloud("################........", "04/02/2115 14:00"));
        cloudLines.add(testUtils.cloud("############............", "04/02/2115 14:10"));
        cloudLines.add(testUtils.cloud("########................", "04/02/2115 14:20"));
        cloudLines.add(testUtils.cloud("####....................", "04/02/2115 14:30"));

        List<Cloud> forSunRegression = filters.filterSunCandidates(cloudLines);

        assertEquals(5, forSunRegression.size());
        Cloud first = forSunRegression.get(0);
        Cloud last = forSunRegression.get(4);

        assertEquals(testUtils.cloud("....################....", "04/02/2115 13:50"), first);
        assertEquals(testUtils.cloud("####....................", "04/02/2115 14:30"), last);
    }
}