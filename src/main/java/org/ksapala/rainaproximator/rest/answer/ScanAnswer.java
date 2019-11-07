package org.ksapala.rainaproximator.rest.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScanAnswer {

    private boolean success;
    private String lastRadarMapTime;
}
