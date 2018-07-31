package org.ksapala.rainaproximator.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.aproximation.scan.Scanner;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilsTests {


    private final long MILLIS = 1538647200000L; // UTC
    private final LocalDateTime TIME = LocalDateTime.of(2018, 10, 4, 10, 0, 0);

    @MockBean
    private Scanner scanner;

    @Test
    public void milisToLocalDateAndTime() {
        assertEquals(TIME, Utils.millisToLocalDateAndTime(MILLIS));
    }

    @Test
    public void localDateAndTimeToMilis() {
        assertEquals(MILLIS, Utils.localDateAndTimeToMillis(TIME));
    }
}
