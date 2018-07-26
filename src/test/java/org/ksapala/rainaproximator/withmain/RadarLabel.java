package org.ksapala.rainaproximator.withmain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RadarLabel extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1925338818452149412L;
	private List<Point> points;
	
	public RadarLabel(ImageIcon i) {
		super(i);
		this.points = new ArrayList<Point>();;
	}

	public void addPoint(Point p) {
		this.points.add(p);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			for (Point point : this.points) {
				drawPoint(g, point.getX(), point.getY());					
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void drawPoint(Graphics g, double x, double y) {
		int xInt = (int) x;
		int yInt = (int) y;
		g.setColor(Color.RED);
		g.drawOval(xInt, yInt, 2, 2);
		g.setColor(Color.BLACK);
		g.drawString(xInt + "," + yInt, xInt, yInt);
	}
}
