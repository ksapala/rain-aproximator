package org.ksapala.rainaproximator.utils;

import java.util.HashMap;
import java.util.Map;

public class Debug {

    private Map<String, Object> map;

    public Debug() {
        map = new HashMap<>();
    }

    public void add(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
