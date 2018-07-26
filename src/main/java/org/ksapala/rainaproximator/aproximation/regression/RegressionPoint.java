package org.ksapala.rainaproximator.aproximation.regression;

import org.ksapala.rainaproximator.aproximation.cloud.Distance;

import java.util.Date;

public class RegressionPoint {
	
	private Distance distance;
	private Date date;
	
	public RegressionPoint(Distance distance, Date date) {
		this.distance = distance;
		this.date = date;
	}
	
	public Distance getDistance() {
		return this.distance;
	}

	public Date getDate() {
		return this.date;
	}

}