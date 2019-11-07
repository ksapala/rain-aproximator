package org.ksapala.rainaproximator.aproximation.weather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
import org.ksapala.rainaproximator.utils.TimeFactory;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

/**
 * @author krzysztof
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherTest {

    @Autowired
    private Configuration configuration;

    @SpyBean
    private TimeFactory timeFactory;

    private TestUtils testUtils;

    @Before
    public void setUp() {
        testUtils = new TestUtils(configuration);
        LocalDateTime fixedTime = testUtils.time("04-02-2115 14:35");
        doReturn(fixedTime).when(timeFactory).now();
    }

    @Test
    public void shouldCheckIsSun() {
        List<Cloud> clouds = new ArrayList<>();
        clouds.add(testUtils.cloud("..........###########.........", "04-02-2115 14:00"));
        clouds.add(testUtils.cloud("........###########...........", "04-02-2115 14:10"));
        clouds.add(testUtils.cloud("......###########.............", "04-02-2115 14:20"));
        clouds.add(testUtils.cloud("....###########...............", "04-02-2115 14:30"));

        Weather weather = new Weather(clouds, timeFactory, configuration.getAlgorithm());

        assertTrue(weather.isSun());
    }

    @Test
    public void shouldCheckIsRain() {
        List<Cloud> clouds = new ArrayList<>();
        clouds.add(testUtils.cloud("##########.........", "04-02-2115 14:00"));
        clouds.add(testUtils.cloud("########...........", "04-02-2115 14:10"));
        clouds.add(testUtils.cloud("######.............", "04-02-2115 14:20"));
        clouds.add(testUtils.cloud("####...............", "04-02-2115 14:30"));

        Weather weather = new Weather(clouds, timeFactory, configuration.getAlgorithm());

        assertFalse(weather.isSun());
    }

    @Test
    public void shouldCheckIsRainInMeantime() {
        List<Cloud> clouds = new ArrayList<>();
        clouds.add(testUtils.cloud("................###########.....", "04-02-2115 14:00"));
        clouds.add(testUtils.cloud("...........###########..........", "04-02-2115 14:10"));
        clouds.add(testUtils.cloud("......###########...............", "04-02-2115 14:20"));
        clouds.add(testUtils.cloud(".###########....................", "04-02-2115 14:30"));

        Weather weather = new Weather(clouds, timeFactory, configuration.getAlgorithm());

        assertFalse(weather.isSun());
    }
}
