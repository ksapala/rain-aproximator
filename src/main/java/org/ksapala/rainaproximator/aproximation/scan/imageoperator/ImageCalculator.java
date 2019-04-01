package org.ksapala.rainaproximator.aproximation.scan.imageoperator;

import org.ksapala.rainaproximator.aproximation.angle.Angle;

public abstract class ImageCalculator {

	protected final Angle angle;

	public ImageCalculator(Angle angle) {
        this.angle = angle;
    }
	
	public abstract double getX(int index);
	public abstract double getY(int index);
}
