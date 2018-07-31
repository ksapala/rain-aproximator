package org.ksapala.rainaproximator.utils;

import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class TimeComponent {


    @Autowired
    private Configuration configuration;


    public DateTimeFormatter getFormatter() {
        return DateTimeFormatter.ofPattern(configuration.getUserTimeFormat());
    }

}
