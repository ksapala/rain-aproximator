package org.ksapala.rainaproximator.aproximation.regression;

import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class RegressionDataProvider {

	protected List<CloudLine> cloudLines;
	
	public RegressionDataProvider(List<CloudLine> cloudLines) {
		this.cloudLines = cloudLines;
		
	}
	
	public abstract Distance getDistance(int index);
	
	public Date getDate(int index) {
		CloudLine cloudLine = this.cloudLines.get(index);
		Date date = cloudLine.getDate();
		return date;
	}
	
	public int size() {
		return this.cloudLines.size();
	}

	/**
	 * @return
	 */
	public List<RegressionPoint> getRegressionPoints() {
		List<RegressionPoint> regressionPoints = new ArrayList<RegressionPoint>(); 
		
		for (int i = 0; i < size(); i++) {
			Distance distance = getDistance(i);
			if (!Distance.INFINITY.equals(distance)) {
				Date date = getDate(i);
				RegressionPoint regressionPoint = new RegressionPoint(distance, date);
				regressionPoints.add(regressionPoint);
			}
		}
		return regressionPoints;
	}

	public abstract String getDescription();
}
