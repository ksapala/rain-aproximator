package org.ksapala.rainaproximator.rest.factory;

import org.junit.Test;
import org.ksapala.rainaproximator.TestUtils;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author krzysztof
 */
public class AproximationBeanFactoryTest {

    @Test
    public void shouldIsTimeAtDay() {
        // given
        LocalDateTime atDay = TestUtils.parseInTest("15-05-2015 12:00");
        LocalDateTime time1 = TestUtils.parseInTest("15-05-2015 13:00");

        LocalDateTime time2 = TestUtils.parseInTest("16-05-2015 12:00");
        LocalDateTime time3 = TestUtils.parseInTest("15-06-2015 12:00");
        LocalDateTime time4 = TestUtils.parseInTest("15-05-2016 12:00");

        // when
        AproximationBeanFactory aproximationBeanFactory = new AproximationBeanFactory();

        // then
        assertTrue(aproximationBeanFactory.isTimeAtDay(time1, atDay));
        assertFalse(aproximationBeanFactory.isTimeAtDay(time2, atDay));
        assertFalse(aproximationBeanFactory.isTimeAtDay(time3, atDay));
        assertFalse(aproximationBeanFactory.isTimeAtDay(time4, atDay));

    }
}