package org.ksapala.rainaproximator.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TimeFactory {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
