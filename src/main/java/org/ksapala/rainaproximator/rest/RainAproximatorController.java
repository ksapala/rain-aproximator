package org.ksapala.rainaproximator.rest;

import org.ksapala.rainaproximator.configuration.Mode;
import org.ksapala.rainaproximator.rest.bean.AproximationBean;
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
    public AproximationBean aproximate(@PathVariable double latitude, @PathVariable double longitude) {
        return rainAproximatorService.aproximate(latitude, longitude);
    }

    // debug

    @GetMapping(path = "/aproximateKrk")
    public AproximationBean aproximateKrk() {
        Mode mode = new Mode(Mode.NAME_AROUND_FINAL, Mode.COMPARE_ACCURACY);
        return rainAproximatorService.aproximateDebug(KRK_LATITUDE, KRK_LONGITUDE, 0, mode);
    }

    @GetMapping(path = "/aproximateKrkTime")
    public AproximationBean aproximateKrkTime() {
        Mode mode = new Mode(Mode.NAME_AROUND_FINAL, Mode.COMPARE_TIME);
        return rainAproximatorService.aproximateDebug(KRK_LATITUDE, KRK_LONGITUDE, 0, mode);
    }

    @GetMapping(path = "/aproximateKrkAround")
    public AproximationBean aproximateKrkAround() {
        Mode mode = new Mode(Mode.NAME_AROUND, Mode.COMPARE_ACCURACY);
        return rainAproximatorService.aproximateDebug(KRK_LATITUDE, KRK_LONGITUDE, 0, mode);
    }

    @GetMapping(path = "/aproximateKrk/{angle}")
    public AproximationBean aproximateKrk(@PathVariable int angle) {
        Mode mode = new Mode(Mode.NAME_STRAIGHT, Mode.COMPARE_ACCURACY);
        return rainAproximatorService.aproximateDebug(KRK_LATITUDE, KRK_LONGITUDE, angle, mode);
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
