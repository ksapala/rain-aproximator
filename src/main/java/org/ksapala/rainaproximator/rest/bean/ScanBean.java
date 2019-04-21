package org.ksapala.rainaproximator.rest.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScanBean {

    private boolean success;
    private String lastRadarMapTime;
}
