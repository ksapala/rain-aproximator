package org.ksapala.rainaproximator.rest;

import org.ksapala.rainaproximator.aproximation.RainAproximator;
import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class RainAproximatorController {

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
        return rainAproximatorService.aproximate(50.077452, 19.981147);
    }

    @GetMapping(path = "/hello")
    public Object hello() {
        return helloService.hello();
    }

}
