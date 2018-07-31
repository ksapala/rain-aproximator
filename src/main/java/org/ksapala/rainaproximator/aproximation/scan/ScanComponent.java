package org.ksapala.rainaproximator.aproximation.scan;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class ScanComponent {

    private final static Logger LOGGER = LogManager.getLogger(ScanComponent.class);

    private Scan scan;

    @Autowired
    private Scanner scanner;

    public ScanComponent() {
    }

//    @Scheduled(fixedRate = 1000)
//    @PostConstruct
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void scan() throws AproximationException {
        LOGGER.debug("Starting to scan.");
        final LocalDateTime start = LocalDateTime.now();

        this.scan = this.scanner.scan();

        LOGGER.debug("Maps scanned at time: " + start);
    }

    public Scan getScan() {
        return scan;
    }
}
