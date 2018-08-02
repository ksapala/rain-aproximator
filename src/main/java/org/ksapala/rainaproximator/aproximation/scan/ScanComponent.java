package org.ksapala.rainaproximator.aproximation.scan;

import org.ksapala.rainaproximator.exception.AproximationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class ScanComponent {

    private final Logger logger = LoggerFactory.getLogger(ScanComponent.class);

    private Scan scan;

    @Autowired
    private Scanner scanner;

    public ScanComponent() {
    }

//    @Scheduled(fixedRate = 1000)
    @PostConstruct
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void scan() throws AproximationException {
        logger.info("Starting to scan.");
        final LocalDateTime start = LocalDateTime.now();

        this.scan = this.scanner.scan();

        logger.info("Maps scanned at time: " + start);
    }

    public Scan getScan() {
        return scan;
    }
}
