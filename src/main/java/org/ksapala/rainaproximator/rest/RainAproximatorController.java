package org.ksapala.rainaproximator.rest;

import org.ksapala.rainaproximator.configuration.Mode;
import org.ksapala.rainaproximator.rest.bean.AproximationBean;
import org.ksapala.rainaproximator.rest.bean.ScanBean;
import org.ksapala.rainaproximator.rest.debug.DebugConstants;
import org.ksapala.rainaproximator.rest.service.FirebaseService;
import org.ksapala.rainaproximator.rest.service.HelloService;
import org.ksapala.rainaproximator.rest.service.RainAproximatorService;
import org.ksapala.rainaproximator.rest.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
public class RainAproximatorController {

    private final double KRK_LATITUDE = DebugConstants.KRK_LATITUDE;
    private final double KRK_LONGITUDE = DebugConstants.KRK_LONGITUDE;

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
        Mode mode = new Mode(Mode.NAME_FULL, Mode.COMPARE_ACCURACY);
        return rainAproximatorService.aproximateDebug(KRK_LATITUDE, KRK_LONGITUDE, 0, mode);
    }

    @GetMapping(path = "/aproximateKrk/{mode}")
    public AproximationBean aproximateKrk(@PathVariable String mode) {
        Mode modeObject = new Mode(mode, Mode.COMPARE_ACCURACY);
        return rainAproximatorService.aproximateDebug(KRK_LATITUDE, KRK_LONGITUDE, 0, modeObject);
    }

    @GetMapping(path = "/aproximateKrk/{mode}/{angle}")
    public AproximationBean aproximateKrk(@PathVariable String mode, @PathVariable int angle) {
        Mode modeObject = new Mode(mode, Mode.COMPARE_ACCURACY);
        return rainAproximatorService.aproximateDebug(KRK_LATITUDE, KRK_LONGITUDE, angle, modeObject);
    }

    @GetMapping(path = "/aproximateKrkTime")
    public AproximationBean aproximateKrkTime() {
        Mode mode = new Mode(Mode.NAME_FULL, Mode.COMPARE_TIME);
        return rainAproximatorService.aproximateDebug(KRK_LATITUDE, KRK_LONGITUDE, 0, mode);
    }

    @GetMapping(path = "/scan")
    public ScanBean scan() {
        return scanService.scan();
    }

    // hello
    @GetMapping(path = "/hello")
    public Object hello() {
        return helloService.hello();
    }

}
