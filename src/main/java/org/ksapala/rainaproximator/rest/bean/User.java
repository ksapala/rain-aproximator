package org.ksapala.rainaproximator.rest.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krzysztof
 */
@Getter
@Setter
@AllArgsConstructor
public class User {

    private String id;
    private double latitude;
    private double longitude;

    @Override
    public String toString() {
        return id + " (" + latitude + "," + longitude + ")";
    }
}
