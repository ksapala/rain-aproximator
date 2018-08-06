package org.ksapala.rainaproximator.aproximation.scan;

import org.ksapala.rainaproximator.exception.AproximationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ScanComponent {

    private final Logger logger = LoggerFactory.getLogger(ScanComponent.class);

    private Scan scan;

    @Autowired
    private Scanner scanner;

    public ScanComponent() {
    }

    @PostConstruct
    @Scheduled(cron = "0 0/2 * * * ?")
    public void scan() throws AproximationException {
        logger.info("Starting to scan.");

        long start = System.currentTimeMillis();
        this.scan = this.scanner.scan();
        long end = System.currentTimeMillis();

        logger.info("Maps scanned successfully. Scan time: " + (end - start));
    }

    public Scan getScan() {
        return scan;
    }
}
