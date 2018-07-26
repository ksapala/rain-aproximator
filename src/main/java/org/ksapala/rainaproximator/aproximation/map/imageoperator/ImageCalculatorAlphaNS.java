package org.ksapala.rainaproximator.aproximation.map.imageoperator;

public class ImageCalculatorAlphaNS extends ImageCalculator {

	public ImageCalculatorAlphaNS(double alpha) {
	    super(alpha);
    }

	@Override
	public double getX(int index) {
		double x = Math.tan(this.alphaRadians) * index;
		return x;
	}

	@Override
	public double getY(int index) {
		return index;
	}
}
