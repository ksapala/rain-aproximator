package org.ksapala.rainaproximator.aproximation.cloud;

import lombok.Getter;
import lombok.Setter;
import org.ksapala.rainaproximator.configuration.Configuration;

import java.time.LocalDateTime;

public class CloudLine {

    // constants
    public static final String SUN_SYMBOL = ".";
	public static final String RAIN_SYMBOL = "#";
	private static final int RGB_TRANSPARENT_GIF_BLACK = 0;
	private static final int RGB_TRANSPARENT_GIF_WHITE = 16777215;

	// fields
    private boolean[] line;
    @Getter
    @Setter
	private LocalDateTime time;
    private final Configuration.Algorithm.Cloud cloudConfiguration;

    // algorithm members
	private Distance rainDistance = null;
	private Distance sunDistance = null;
	private PatternBuilder patternBuilder;

	public CloudLine(Configuration.Algorithm.Cloud cloudConfiguration, boolean[] line, LocalDateTime time) {
        this.cloudConfiguration = cloudConfiguration;
        this.line = line;
		this.time = time;
		this.patternBuilder = new PatternBuilder();
	}
	
	public void addRgb(int rgb, int index) {
	    if (rgb == RGB_TRANSPARENT_GIF_BLACK || rgb == RGB_TRANSPARENT_GIF_WHITE) {
	    	setRain(index, false);	
	    } else {
	    	setRain(index, true);	
	    }
    }

	private void setRain(int index, boolean value) {
	    this.line[index] = value;
    }
	
	public String toString() {
		return getLineAsString() + " date: " + getTime();
	}
	
	public String getLineAsString() {
		String result = "";
		for (Boolean isRain : this.line) {
			if (isRain) {
			    result += RAIN_SYMBOL;
			} else {
				result += SUN_SYMBOL;	
			}
        }
		return result;
	}
	
	public void print() {
		System.out.println(toString());
	}
	
	/**
	 *
     */
	public void smoothLine() {
		smoothMiddle();
		smoothStart();
	}

	/**
	 * 
	 */
	private void smoothStart() {
		for (int i = cloudConfiguration.getReplaceHolesStartMin(); i < cloudConfiguration.getReplaceHolesStartMax(); i++) {
			boolean[] startTruePattern = this.patternBuilder.buildStartTrue(i + 1);
			boolean[] allFalsePattern = this.patternBuilder.buildAllFalse(i + 1);
			replaceStart(startTruePattern, allFalsePattern);
			
			boolean[] startFalsePattern = this.patternBuilder.buildStartFalse(i + 1);
			boolean[] allTruePattern = this.patternBuilder.buildAllTrue(i + 1);
			replaceStart(startFalsePattern, allTruePattern);
		}
    }

	/**
	 * 
	 */
	private void smoothMiddle() {
		for (int i = cloudConfiguration.getReplaceHolesMin(); i <= cloudConfiguration.getReplaceHolesMax(); i++) {
			boolean[] middleFalsePattern = this.patternBuilder.buildMiddleFalse(i + 2);
			boolean[] allTruePattern = this.patternBuilder.buildAllTrue(i + 2);
			replacePattern(middleFalsePattern, allTruePattern);
			
			boolean[] middleTruePattern = this.patternBuilder.buildMiddleTrue(i + 2);
			boolean[] allFalsePattern = this.patternBuilder.buildAllFalse(i + 2);
			replacePattern(middleTruePattern, allFalsePattern);
        }
    }

	/**
	 * @param matchPattern
	 * @param replacePattern
	 */
	private void replaceStart(boolean[] matchPattern, boolean[] replacePattern) {
		boolean lineEqualsFromIndex = lineEqualsFromIndex(0, matchPattern);
    	if (lineEqualsFromIndex) {
    		replacePatternFromIndex(0, replacePattern);
    	}
    }

	/**
	 * @param matchPattern
	 * @param replacePattern
	 */
	public void replacePattern(boolean[] matchPattern, boolean[] replacePattern) {
		for (int i = 0; i < this.line.length - matchPattern.length + 1; i++) {
			boolean lineEqualsFromIndex = lineEqualsFromIndex(i, matchPattern);
			if (lineEqualsFromIndex) {
				replacePatternFromIndex(i, replacePattern);
				i += replacePattern.length - 2; // skip replaced length, go back by one (-1), ignore loop increment (-1)
			}
		}
	}
	
	/**
	 * @param index
	 * @param replacePattern
	 */
	private void replacePatternFromIndex(int index, boolean[] replacePattern) {
		for (int i = 0; i < replacePattern.length; i++) {
	        this.line[index + i] = replacePattern[i];
        }
    }

	/**
	 * @param index
	 * @param matchPattern
	 * @return
	 */
	private boolean lineEqualsFromIndex(int index, boolean[] matchPattern) {
		for (int i = 0; i < matchPattern.length; i++) {
            if (this.line[index + i] != matchPattern[i]) {
            	return false;
            }
        }
		return true;
    }

	/**
	 * @return
	 */
	public Distance getRainDistance() {
		if (this.rainDistance == null) {
			this.rainDistance = getDistanceForValue(true);
		}
		return this.rainDistance;
    }

	/**
	 * @return
	 */
	public Distance getSunDistance() {
		if (this.sunDistance == null) {
			this.sunDistance = getDistanceForValue(false);
		}
		return this.sunDistance;
	}
	
	/**
	 * @param value
	 * @return
	 */
	public Distance getDistanceForValue(boolean value) {
		for (int i = 0; i < this.line.length; i++) {
	        if (this.line[i] == value) {
	        	return new Distance(i);
	        }
        }
	    return Distance.INFINITY;
	}

}
