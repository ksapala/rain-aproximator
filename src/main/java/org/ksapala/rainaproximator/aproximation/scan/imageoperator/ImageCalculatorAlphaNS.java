package org.ksapala.rainaproximator.aproximation.scan.imageoperator;

public class ImageCalculatorAlphaNS extends ImageCalculator {

	public ImageCalculatorAlphaNS(double alpha) {
	    super(alpha);
    }

	@Override
	public double getX(int index) {
        return Math.tan(this.alphaRadians) * index;
	}

	@Override
	public double getY(int index) {
		return index;
	}
}
