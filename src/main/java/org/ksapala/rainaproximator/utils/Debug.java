package org.ksapala.rainaproximator.utils;

import lombok.Getter;
import lombok.Setter;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;

import java.util.List;

@Getter
@Setter
public class Debug {

    private String wind;
    private List<CloudLine> cloudLines;

    public Debug() {
    }

}
