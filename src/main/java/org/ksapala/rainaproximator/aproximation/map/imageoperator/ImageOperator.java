package org.ksapala.rainaproximator.aproximation.map.imageoperator;

public class ImageOperator {

	private boolean alphaW;
	private boolean alphaE;
	private boolean alphaN;
	private double alpha;
	private int samplesCount;
	
	public ImageOperator(double alpha, int samplesCount) {
	    this.alpha = alpha;
		this.samplesCount = samplesCount;
		this.alphaN = alpha >= 315 || alpha < 45;
		this.alphaE = alpha >= 45 && alpha < 135;
	    this.alphaW = alpha >= 225 && alpha < 315;
	}
	
	public ImageIterator getImageIterator() {
		if (this.alphaN || this.alphaE) {
			return new ImageIteratorPlus(this.samplesCount);
		}
		return new ImageIteratorMinus(this.samplesCount);
	}
	public ImageCalculator getImageCalculator() {
		if (this.alphaW || this.alphaE) {
			return new ImageCalculatorAlphaWE(this.alpha);
		}
		return new ImageCalculatorAlphaNS(this.alpha);
	}
	
}
