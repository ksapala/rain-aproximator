package org.ksapala.rainaproximator.aproximation.map.imageoperator;

import java.util.Iterator;
import java.util.function.Consumer;

public abstract class ImageIterator implements Iterator<Integer> {


	protected int index;
	protected int samplesCount;
	
	public ImageIterator(int index, int samplesCount) {
	    super();
	    this.index = index;
	    this.samplesCount = samplesCount;
    }

	@Override
    public void remove() {
    }

	@Override
    public void forEachRemaining(Consumer<? super Integer> action) {
    }

}
