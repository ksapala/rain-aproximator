package org.ksapala.rainaproximator.aproximation.scan.imageoperator;

import org.ksapala.rainaproximator.aproximation.angle.Angle;

public class ImageOperator {

	private Angle angle;
	private int samplesCount;
	
	public ImageOperator(double alpha, int samplesCount) {
	    this.angle = new Angle(alpha);
		this.samplesCount = samplesCount;
	}
	
	public ImageIterator getImageIterator() {
		if (angle.isN() || angle.isE()) {
			return new ImageIteratorPlus(this.samplesCount);
		}
		return new ImageIteratorMinus(this.samplesCount);
	}
	public ImageCalculator getImageCalculator() {
		if (angle.isW() || angle.isE()) {
			return new ImageCalculatorAlphaWE(angle);
		}
		return new ImageCalculatorAlphaNS(angle);
	}
	
}
