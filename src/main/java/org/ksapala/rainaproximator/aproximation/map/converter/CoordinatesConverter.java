package org.ksapala.rainaproximator.aproximation.map.converter;


public class CoordinatesConverter {

	public static final int IMAGE_WIDTH = 800;
	public static final int IMAGE_HEIGHT = 800;

	public static final int BASE_X = 404;
	public static final int BASE_Y = 400;

	public static final double LON_MIN = 13.5;
	public static final double LON_MAX = 25.5;
	public static final double LON_SIZE = LON_MAX - LON_MIN;
	
	public static final double LAT_MAX = 56.3;
	public static final double LAT_MIN = 48.5;
	public static final double LAT_SIZE = LAT_MAX - LAT_MIN;
	
	public static final double LON_BASE = 19.5;
	public static final double LAT_BASE = 52.4;
	
	public static final double LAT_BREAK = 54;
	public static final double LON_CURVE_FACTOR = 1;
	
	public CoordinatesConverter() {
	}

	public double[] convert(double latitude, double longitude) {
		double x = (longitude - LON_BASE) * (IMAGE_WIDTH - BASE_X) / (LON_MAX - LON_BASE) + BASE_X;
		double y = IMAGE_HEIGHT - ((latitude - LAT_BASE) * (IMAGE_HEIGHT - BASE_Y) / (LAT_MAX - LAT_BASE) + BASE_Y);
		
		double xDifference = longitude - LON_BASE;
		double yDifference = (LAT_BREAK - latitude);
		
		double shiftX = Math.abs(xDifference) * (LON_CURVE_FACTOR + Math.abs(yDifference));
		double shiftY = Math.abs(xDifference) *  Math.abs(yDifference);  
		
		y -= shiftY;
		
		boolean xDPLus = xDifference > 0;
		boolean yDPLus = yDifference > 0;
		
		if (xDPLus != yDPLus) {
			x -= shiftX;
		} else {
			x += shiftX;
		}
		double[] convert = new double[] {x, y};
		return convert;
	}
}
