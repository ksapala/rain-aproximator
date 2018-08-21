package org.ksapala.rainaproximator.rest;

import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;
import org.ksapala.rainaproximator.rest.bean.ScanResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RainAproximatorController {


    private final double KRK_LATITUDE = 50.077452;
    private final double KRK_LONGITUDE = 19.981147;

    @Autowired
    private RainAproximatorService rainAproximatorService;

    @Autowired
    private ScanService scanService;

    @Autowired
    private HelloService helloService;

    @GetMapping(path = "/aproximate/{latitude}/{longitude}")
    public AproximationResultBean aproximate(@PathVariable double latitude, @PathVariable double longitude) {
        return rainAproximatorService.aproximate(latitude, longitude);
    }


    @GetMapping(path = "/aproximateKrk")
    public AproximationResultBean aproximateKrk() {
        return rainAproximatorService.aproximate(KRK_LATITUDE, KRK_LONGITUDE);
    }

    @GetMapping(path = "/aproximateKrk/{wind}")
    public AproximationResultBean aproximateKrkWind(@PathVariable double wind) {
        return rainAproximatorService.aproximateDebug(KRK_LATITUDE, KRK_LONGITUDE, wind);
    }

    @GetMapping(path = "/scan")
    public ScanResultBean scan() {
        return scanService.scan();
    }

    @GetMapping(path = "/hello")
    public Object hello() {
        return helloService.hello();
    }

}
