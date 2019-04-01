package org.ksapala.rainaproximator.aproximation.scan.imageoperator;

import org.ksapala.rainaproximator.aproximation.angle.Angle;

public class ImageCalculatorAlphaWE extends ImageCalculator {

	public ImageCalculatorAlphaWE(Angle angle) {
	    super(angle);
    }

	@Override
	public double getX(int index) {
		return index;
	}

	@Override
	public double getY(int index) {
        return 1.0 / angle.tan() * index;
	}

}
