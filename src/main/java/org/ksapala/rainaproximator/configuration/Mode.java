package org.ksapala.rainaproximator.configuration;

import lombok.Getter;

public class Mode {

    public final static String NAME_FULL = "full";
    public final static String NAME_STRAIGHT = "straight";
    public final static String NAME_AROUND = "around";
    public final static String NAME_AROUND_FINAL = "aroundFinal";

    public final static String COMPARE_TIME = "time";
    public final static String COMPARE_ACCURACY = "accuracy";

    @Getter
    private String name;

    @Getter
    private String compare;


    public Mode(String name, String compare) {
        validateModeName(name);
        this.name = name;
        this.compare = compare;
    }

    public Mode(Configuration.Algorithm.Mode mode) {
        this(mode.getName(), mode.getCompare());
    }

    private void validateModeName(String name) {
        if (!name.equals(NAME_FULL)
                && !name.equals(NAME_STRAIGHT)
                && !name.equals(NAME_AROUND)
                && !name.equals(NAME_AROUND_FINAL)) {
            throw new IllegalArgumentException("Invalid aproximation mode name:" + name);
        }
    }
}
