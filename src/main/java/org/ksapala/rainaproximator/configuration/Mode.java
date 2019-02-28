package org.ksapala.rainaproximator.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Mode {

    public final static String NAME_AROUND_FINAL = "aroundFinal";
    public final static String NAME_AROUND = "around";
    public final static String NAME_STRAIGHT = "straight";

    public final static String COMPARE_TIME= "time";
    public final static String COMPARE_ACCURACY = "accuracy";

    @Getter
    private String name;

    @Getter
    private String compare;

    public Mode(Configuration.Algorithm.Mode mode) {
        this.name = mode.getName();
        this.compare = mode.getCompare();
    }
}
