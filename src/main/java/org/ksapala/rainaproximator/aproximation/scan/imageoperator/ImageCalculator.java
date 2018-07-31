package org.ksapala.rainaproximator.aproximation.scan.imageoperator;

public abstract class ImageCalculator {

	double alpha;
	double alphaRadians;
	
	public ImageCalculator(double alpha) {
	    this.alpha = alpha;
	    this.alphaRadians = Math.toRadians(alpha);
    }
	
	public abstract double getX(int index);
	public abstract double getY(int index);
}
