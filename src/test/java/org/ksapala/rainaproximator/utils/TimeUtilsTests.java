package org.ksapala.rainaproximator.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.scan.Scanner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeUtilsTests {


    private final long MILLIS = 1538647200000L; // UTC
    private final LocalDateTime TIME = LocalDateTime.of(2018, 10, 4, 10, 0, 0);

    @MockBean
    private Scanner scanner;

    @Test
    public void milisToLocalDateAndTime() {
        assertEquals(TIME, TimeUtils.millisToDate(MILLIS));
    }

    @Test
    public void localDateAndTimeToMilis() {
        assertEquals(MILLIS, TimeUtils.dateToMillis(TIME));
    }
}
