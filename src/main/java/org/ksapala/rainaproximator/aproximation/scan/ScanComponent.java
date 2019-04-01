package org.ksapala.rainaproximator.aproximation.scan;

import org.ksapala.rainaproximator.exception.AproximationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class ScanComponent {

    private final Logger logger = LoggerFactory.getLogger(ScanComponent.class);

    private Optional<Scan> scan;

    @Autowired
    private Scanner scanner;

    public ScanComponent() {
        scan = Optional.empty();
    }

    @PostConstruct
    @Scheduled(cron = "0 0/5 * * * ?")
    public void scan() {
        try {
            doScan();
        } catch (AproximationException e) {
            logger.error("Error while scanning with Scan Component.", e);
        }
    }


    public void doScan() throws AproximationException {
        logger.info("Starting to scan.");

        long start = System.currentTimeMillis();
        scan = Optional.ofNullable(scanner.scan());
        long end = System.currentTimeMillis();

        logger.info("Maps scanned successfully. Scan time: " + (end - start));
    }

    public Optional<Scan> getScan() {
        return scan;
    }
}
