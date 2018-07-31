package org.ksapala.rainaproximator.aproximation.scan.imageoperator;


public class ImageIteratorMinus extends ImageIterator {

	public ImageIteratorMinus(int samplesCount) {
		super(0, samplesCount);
	}

	@Override
    public boolean hasNext() {
	    return this.index > -this.samplesCount;
    }

	@Override
    public Integer next() {
	    return this.index--;
    }
}
