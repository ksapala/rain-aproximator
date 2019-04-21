package org.ksapala.rainaproximator.rest.service;

import org.ksapala.rainaproximator.aproximation.scan.ScanComponent;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.rest.bean.ScanBean;
import org.ksapala.rainaproximator.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScanService {


    @Autowired
    private ScanComponent scanComponent;

    @Autowired
    private Configuration configuration;

    public ScanService() {
    }

    /**
     * @return
     */
    public ScanBean scan() {
        scanComponent.scan();

        if (scanComponent.getScan().isPresent()) {
            LocalDateTime lastMapTime = scanComponent.getScan().get().getLastMapTime();
            return new ScanBean(true, lastMapTime.format(TimeUtils.getFormatter(configuration)));
        }
        return new ScanBean(false, null);
    }


}
