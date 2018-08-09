package org.ksapala.rainaproximator.rest;

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
    private HelloService helloService;

    @GetMapping(path = "/aproximate/{latitude}/{longitude}")
    public Object aproximate(@PathVariable double latitude, @PathVariable double longitude) {
        return rainAproximatorService.aproximate(latitude, longitude);
    }


    @GetMapping(path = "/aproximateKrk")
    public Object aproximateKrk() {
        return rainAproximatorService.aproximate(KRK_LATITUDE, KRK_LONGITUDE);
    }

    @GetMapping(path = "/aproximateKrk/{wind}")
    public Object aproximateKrkWind(@PathVariable double wind) {
        return rainAproximatorService.aproximateDebug(KRK_LATITUDE, KRK_LONGITUDE, wind);
    }

    @GetMapping(path = "/hello")
    public Object hello() {
        return helloService.hello();
    }

}
