package org.ksapala.rainaproximator.aproximation.scan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScannerTest {

    @Autowired
    private Scanner scanner;

    @Autowired
    private Configuration configuration;

    @MockBean
    private ScanComponent scanComponent;


    @Test
    public void scan() throws AproximationException {
        Scan scan = scanner.scan();

        assertNotNull(scan);
        assertNotNull(scan.getLastMapTime());
        assertNotNull(scan.getMaps());

        int imageIdentifiersCount  = configuration.getScanner().getRadarImageIdentifiers().size();
        if (imageIdentifiersCount > 1) {
            int actualMapsCount = scan.getMaps().size();
            assertTrue(actualMapsCount <= imageIdentifiersCount);

            ScannedMap last = scan.getMaps().get(actualMapsCount - 1);
            ScannedMap lastMinusOne = scan.getMaps().get(actualMapsCount - 2);

            LocalDateTime timeLast = last.getTime();
            LocalDateTime timeLastMinusOne = lastMinusOne.getTime();

            assertTrue(last.isClear());
            assertTrue(lastMinusOne.isClear());

            assertEquals(scan.getLastMapTime(), timeLast);
            assertEquals(timeLast.minusMinutes(configuration.getScanner().getRadarMapTimeIntevalMinutes()), timeLastMinusOne);
        }

    }
}