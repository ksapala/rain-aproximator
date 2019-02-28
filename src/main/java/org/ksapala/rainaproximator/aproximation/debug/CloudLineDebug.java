package org.ksapala.rainaproximator.aproximation.debug;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;

@AllArgsConstructor
@Getter
public class CloudLineDebug {

    private CloudLine cloudLine;
    private boolean used;

}
