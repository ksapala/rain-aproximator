package org.ksapala.rainaproximator.rest.answer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.TestUtils;
import org.ksapala.rainaproximator.aproximation.result.Aproximation;
import org.ksapala.rainaproximator.aproximation.result.AproximationResult;
import org.ksapala.rainaproximator.aproximation.result.AproximationResultType;
import org.ksapala.rainaproximator.utils.TimeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

/**
 * @author krzysztof
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AproximationAnswerFactoryTest {

    @Autowired
    private AproximationAnswerFactory aproximationAnswerFactory;

    @SpyBean
    private TimeFactory timeFactory;

    @Before
    public void setUp() {
        LocalDateTime fixedTime = TestUtils.time("15-05-2015 12:00");
        doReturn(fixedTime).when(timeFactory).now();
    }

    @Test
    public void shouldCheckNotificationSuggestedTrueRain10Minutes() {
        // given
        Aproximation aproximation = Aproximation.builder()
                .aproximationResult(AproximationResult.builder()
                        .type(AproximationResultType.RAIN_AT_TIME)
                        .time(TestUtils.time("15-05-2015 12:10"))
                        .build())
                .build();
        // then
        assertTrue(aproximationAnswerFactory.isNotificationSuggested(aproximation));
    }

    @Test
    public void shouldCheckNotificationSuggestedTrueRain110Minutes() {
        // given
        Aproximation aproximation = Aproximation.builder()
                .aproximationResult(AproximationResult.builder()
                        .type(AproximationResultType.RAIN_AT_TIME)
                        .time(TestUtils.time("15-05-2015 13:50"))
                        .build())
                .build();
        // then
        assertTrue(aproximationAnswerFactory.isNotificationSuggested(aproximation));
    }

    @Test
    public void shouldCheckNotificationSuggestedFalseRain130Minutes() {
        // given
        Aproximation aproximation = Aproximation.builder()
                .aproximationResult(AproximationResult.builder()
                        .type(AproximationResultType.RAIN_AT_TIME)
                        .time(TestUtils.time("15-05-2015 14:10"))
                        .build())
                .build();
        // then
        assertFalse(aproximationAnswerFactory.isNotificationSuggested(aproximation));
    }

    @Test
    public void shouldCheckNotificationSuggestedOtherTypes() {
        // given
        Aproximation aproximationRainUnsure = Aproximation.builder()
                .aproximationResult(AproximationResult.builder()
                        .type(AproximationResultType.RAIN_GOES_AWAY)
                        .build())
                .build();
        Aproximation aproximationRainUnknown = Aproximation.builder()
                .aproximationResult(AproximationResult.builder()
                        .type(AproximationResultType.RAIN_UNKNOWN)
                        .build())
                .build();
        Aproximation aproximationSunAtTime = Aproximation.builder()
                .aproximationResult(AproximationResult.builder()
                        .type(AproximationResultType.SUN_AT_TIME)
                        .build())
                .build();
        Aproximation aproximationSunUnsure = Aproximation.builder()
                .aproximationResult(AproximationResult.builder()
                        .type(AproximationResultType.SUN_GOES_AWAY)
                        .build())
                .build();
        Aproximation aproximationSunUnknown= Aproximation.builder()
                .aproximationResult(AproximationResult.builder()
                        .type(AproximationResultType.SUN_UNKNOWN)
                        .build())
                .build();
        // then
        assertFalse(aproximationAnswerFactory.isNotificationSuggested(aproximationRainUnsure));
        assertFalse(aproximationAnswerFactory.isNotificationSuggested(aproximationRainUnknown));
        assertTrue(aproximationAnswerFactory.isNotificationSuggested(aproximationSunAtTime));
        assertTrue(aproximationAnswerFactory.isNotificationSuggested(aproximationSunUnsure));
        assertTrue(aproximationAnswerFactory.isNotificationSuggested(aproximationSunUnknown));
    }

    @Test
    public void shouldIsTimeAtDay() {
        // given
        LocalDateTime atDay = TestUtils.time("15-05-2015 12:00");
        LocalDateTime time1 = TestUtils.time("15-05-2015 13:00");

        LocalDateTime time2 = TestUtils.time("16-05-2015 12:00");
        LocalDateTime time3 = TestUtils.time("15-06-2015 12:00");
        LocalDateTime time4 = TestUtils.time("15-05-2016 12:00");

        // then
        assertTrue(aproximationAnswerFactory.isTimeAtDay(time1, atDay));
        assertFalse(aproximationAnswerFactory.isTimeAtDay(time2, atDay));
        assertFalse(aproximationAnswerFactory.isTimeAtDay(time3, atDay));
        assertFalse(aproximationAnswerFactory.isTimeAtDay(time4, atDay));

    }
}