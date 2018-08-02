package org.ksapala.rainaproximator.withmain;

import org.ksapala.rainaproximator.aproximation.scan.converter.CoordinatesConverter;

import java.awt.*;

public class RadarMapTest {
	
	public RadarMapTest() {
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		testJFrame();
	}


	public static void printArray(double a[]) {
		System.out.println(a[0] + ", " + a[1]);
	}


//	public static void testJFrame() throws Exception {
//		JFrame frame = new JFrame("Radar map test");
//		JPanel p = new JPanel();
//
//		File file = RadarMapGetter.getRadarMapFile("30");
//		file = getRadarMapGridFile();
//
//		BufferedImage image = ImageIO.read(file);
//		RadarLabel label = new RadarLabel(new ImageIcon(image));
//
//		label.addPoint(getPointFromGeo(CoordinatesConverter.LON_BASE, CoordinatesConverter.LAT_BASE));
//
//		label.addPoint(getPointFromGeo(14, 55));
//		label.addPoint(getPointFromGeo(15, 55));
//		label.addPoint(getPointFromGeo(16, 55));
//		label.addPoint(getPointFromGeo(17, 55));
//		label.addPoint(getPointFromGeo(18, 55));
//		label.addPoint(getPointFromGeo(19, 55));
//		label.addPoint(getPointFromGeo(20, 55));
//		label.addPoint(getPointFromGeo(21, 55));
//		label.addPoint(getPointFromGeo(22, 55));
//		label.addPoint(getPointFromGeo(23, 55));
//		label.addPoint(getPointFromGeo(24, 55));
//
//		label.addPoint(getPointFromGeo(24, 55));
//		label.addPoint(getPointFromGeo(24, 54));
//		label.addPoint(getPointFromGeo(24, 53));
//		label.addPoint(getPointFromGeo(24, 52));
//		label.addPoint(getPointFromGeo(24, 51));
//		label.addPoint(getPointFromGeo(24, 50));
//
//		label.addPoint(getPointFromGeo(14, 53));
//		label.addPoint(getPointFromGeo(15, 53));
//		label.addPoint(getPointFromGeo(16, 53));
//		label.addPoint(getPointFromGeo(17, 53));
//		label.addPoint(getPointFromGeo(18, 53));
//		label.addPoint(getPointFromGeo(19, 53));
//		label.addPoint(getPointFromGeo(20, 53));
//		label.addPoint(getPointFromGeo(21, 53));
//		label.addPoint(getPointFromGeo(22, 53));
//		label.addPoint(getPointFromGeo(23, 53));
//		label.addPoint(getPointFromGeo(24, 53));
//
//		label.addPoint(getPointFromGeo(14, 50));
//		label.addPoint(getPointFromGeo(15, 50));
//		label.addPoint(getPointFromGeo(16, 50));
//		label.addPoint(getPointFromGeo(17, 50));
//		label.addPoint(getPointFromGeo(18, 50));
//		label.addPoint(getPointFromGeo(19, 50));
//		label.addPoint(getPointFromGeo(20, 50));
//		label.addPoint(getPointFromGeo(21, 50));
//		label.addPoint(getPointFromGeo(22, 50));
//		label.addPoint(getPointFromGeo(23, 50));
//		label.addPoint(getPointFromGeo(24, 50));
//
//		p.add(label);
//		frame.add(p);
//		frame.pack();
//
//
//		frame.setVisible(true);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}

	
	private static Point getPointFromGeo(double longitude, double latitude) throws Exception {
		double[] convert = new CoordinatesConverter().convert(longitude, latitude);
		return new Point((int) convert[0], (int) convert[1]);
	}



//	public static File getRadarMapGridFile() {
//		String path = Settings.get(Property.RADAR_MAP_FILE_PATH_TEST);
//		String gridFile  = Settings.get(Property.RADAR_MAP_GRID_FILE_PATH);
//		File outputfile = new File(path + gridFile);
//		return outputfile;
//	}
	
	
}
