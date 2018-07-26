package org.ksapala.rainaproximator.aproximation;

import org.ksapala.rainaproximator.messages.Messages;

public enum AproximationResultType {
    ERROR(Messages.getString("AproximationResult.0"), 5),  //$NON-NLS-1$
	RAIN_AT_TIME(Messages.getString("AproximationResult.0"), 5),  //$NON-NLS-1$
	SUN_AT_TIME(Messages.getString("AproximationResult.1"), 4),  //$NON-NLS-1$
	RAIN_UNSURE(Messages.getString("AproximationResult.2"), 3), //$NON-NLS-1$
	SUN_UNSURE(Messages.getString("AproximationResult.3"), 2), //$NON-NLS-1$
	RAIN_UNKNOWN(Messages.getString("AproximationResult.4"), 1),  //$NON-NLS-1$
	SUN_UNKNOWN(Messages.getString("AproximationResult.5"), 0);  //$NON-NLS-1$

	private String info;
	private int weatherDominance;

	private AproximationResultType(String info, int weatherDominance) {
		this.info = info;
		this.weatherDominance = weatherDominance;
    }
	
	public String getUserFriendlyInfo() {
		return this.info;
	}

	public int getWeatherDominance() {
		return this.weatherDominance;
	}

}