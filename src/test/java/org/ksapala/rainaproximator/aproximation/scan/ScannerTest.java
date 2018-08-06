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

        int mapsCount = configuration.getScanner().getRadarImageIdentifiers().size();
        if (mapsCount > 1) {
            assertTrue(scan.getMaps().size() == mapsCount);

            LocalDateTime timeLast = scan.getMaps().get(mapsCount - 1).getTime();
            LocalDateTime timeLastMinusOne = scan.getMaps().get(mapsCount - 2).getTime();

            assertEquals(scan.getLastMapTime(), timeLast);
            assertEquals(timeLast.minusMinutes(configuration.getScanner().getRadarMapTimeIntevalMinutes()), timeLastMinusOne);
        }

    }
}