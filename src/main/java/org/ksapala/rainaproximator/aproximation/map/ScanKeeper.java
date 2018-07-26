package org.ksapala.rainaproximator.aproximation.map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScanKeeper {

    private final static Logger LOGGER = LogManager.getLogger(ScanKeeper.class);

    private Scan scan;
    private final Scanner scanner;

    @Autowired
    public ScanKeeper(Configuration configuration) {
        this.scanner = new Scanner(configuration.getScanner());
        try {
            scan();
        } catch (AproximationException e) {
            LOGGER.error("Exception on initial scan.", e);
        }
    }

//    @Scheduled(fixedRate = 1000)
    @Scheduled(cron = "0 0/1 * * * ?")
    public void scan() throws AproximationException {
        final LocalDateTime start = LocalDateTime.now();
        this.scan = this.scanner.scan();

        LOGGER.debug("Maps scanned at time: " + start);
    }

    public Scan getScan() {
        return scan;
    }
}
