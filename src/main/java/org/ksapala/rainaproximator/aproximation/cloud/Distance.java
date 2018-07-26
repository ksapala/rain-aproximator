package org.ksapala.rainaproximator.aproximation.cloud;

public class Distance {

	public static final Distance INFINITY = new Distance(-1);
	
	private int value;
	
	public Distance(int value) {
		this.value = value;
    }
	
	public int getValue() {
		return this.value;
	}

}