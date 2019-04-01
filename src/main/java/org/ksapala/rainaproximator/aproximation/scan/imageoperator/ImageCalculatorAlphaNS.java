package org.ksapala.rainaproximator.aproximation.scan.imageoperator;

import org.ksapala.rainaproximator.aproximation.angle.Angle;

public class ImageCalculatorAlphaNS extends ImageCalculator {

	public ImageCalculatorAlphaNS(Angle angle) {
	    super(angle);
    }

	@Override
	public double getX(int index) {
        return angle.tan() * index;
	}

	@Override
	public double getY(int index) {
		return index;
	}
}
