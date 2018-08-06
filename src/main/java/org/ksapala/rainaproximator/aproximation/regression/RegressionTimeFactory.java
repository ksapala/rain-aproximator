package org.ksapala.rainaproximator.aproximation.regression;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RegressionTimeFactory {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
