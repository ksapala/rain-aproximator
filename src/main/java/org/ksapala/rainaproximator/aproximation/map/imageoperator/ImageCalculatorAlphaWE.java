package org.ksapala.rainaproximator.aproximation.map.imageoperator;

public class ImageCalculatorAlphaWE extends ImageCalculator {

	public ImageCalculatorAlphaWE(double alpha) {
	    super(alpha);
    }

	@Override
	public double getX(int index) {
		return index;
	}

	@Override
	public double getY(int index) {
		double y = 1.0 / Math.tan(this.alphaRadians) * index;
		return y;
	}

}
