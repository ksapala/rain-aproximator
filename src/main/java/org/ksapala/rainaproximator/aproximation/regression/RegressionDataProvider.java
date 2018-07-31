package org.ksapala.rainaproximator.aproximation.regression;

import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.aproximation.cloud.Distance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class RegressionDataProvider {

	protected List<CloudLine> cloudLines;
	
	public RegressionDataProvider(List<CloudLine> cloudLines) {
		this.cloudLines = cloudLines;
		
	}
	
	public abstract Distance getDistance(int index);
	
	public LocalDateTime getTime(int index) {
		CloudLine cloudLine = this.cloudLines.get(index);
		LocalDateTime time = cloudLine.getTime();
		return time;
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
                LocalDateTime time = getTime(i);
				RegressionPoint regressionPoint = new RegressionPoint(distance, time);
				regressionPoints.add(regressionPoint);
			}
		}
		return regressionPoints;
	}

	public abstract String getDescription();
}
