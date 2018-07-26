package org.ksapala.rainaproximator.rest;

import org.ksapala.rainaproximator.rest.bean.AproximationResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RainAproximatorController {

    @Autowired
    private RainAproximatorService rainAproximatorService;

    @GetMapping(path = "/aproximate/{latitude}/{longitude}")
    public Object aproximate(@PathVariable double latitude, @PathVariable double longitude) {
        return AproximationResultBean.HELLO;
//        return rainAproximatorService.aproximate(latitude, longitude);
    }

}
