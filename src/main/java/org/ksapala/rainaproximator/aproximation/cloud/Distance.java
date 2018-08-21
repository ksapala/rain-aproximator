package org.ksapala.rainaproximator.aproximation.cloud;

import java.util.Objects;

public class Distance {

	public static final Distance INFINITY = new Distance(-1);
	
	private int value;
	
	public Distance(int value) {
		this.value = value;
    }
	
	public int getValue() {
		return this.value;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance = (Distance) o;
        return value == distance.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Distance{" +
                "value=" + value +
                '}';
    }
}