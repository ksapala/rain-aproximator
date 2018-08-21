package org.ksapala.rainaproximator.rest.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScanResultBean {

    private boolean success;
    private String lastRadarMapTime;
}
