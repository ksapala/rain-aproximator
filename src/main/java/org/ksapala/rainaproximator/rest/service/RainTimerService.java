package org.ksapala.rainaproximator.rest.service;

import org.ksapala.rainaproximator.configuration.Mode;
import org.ksapala.rainaproximator.rest.bean.AproximationBean;
import org.ksapala.rainaproximator.rest.bean.ScanBean;
import org.ksapala.rainaproximator.rest.debug.DebugConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author krzysztof
 */
@Component
public class RainTimerService {

    private final Logger logger = LoggerFactory.getLogger(RainTimerService.class);

    @Autowired
    private RainAproximatorService rainAproximatorService;

    @Autowired
    private ScanService scanService;

    @Autowired
    private FirebaseService firebaseService;

    public void scanAproximateAndNotify() {
        logger.info("All operations start.");
        ScanBean scan = scanService.scan();

        Mode mode = new Mode(Mode.NAME_FULL, Mode.COMPARE_ACCURACY);
        AproximationBean aproximationBean = rainAproximatorService.aproximateDebug(DebugConstants.KRK_LATITUDE, DebugConstants.KRK_LONGITUDE, 0, mode);

        firebaseService.notify(aproximationBean);
        logger.info("All operations end.");
    }
}
