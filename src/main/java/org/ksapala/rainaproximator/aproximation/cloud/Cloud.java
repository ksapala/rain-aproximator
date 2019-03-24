package org.ksapala.rainaproximator.aproximation.cloud;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.ksapala.rainaproximator.aproximation.image.RainImage;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.serializer.CloudSerializer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@JsonSerialize(using = CloudSerializer.class)
public class Cloud {

    // constants
    public static final String SUN_SYMBOL = ".";
	public static final String RAIN_SYMBOL = "#";

	// fields
    private boolean[] line;
    @Getter
    @Setter
	private LocalDateTime time;
    private final Configuration.Algorithm.Cloud cloudConfiguration;

    // algorithm members
	private Distance rainDistance = null;
	private Distance sunDistance = null;

	public Cloud(Configuration.Algorithm.Cloud cloudConfiguration, boolean[] line, LocalDateTime time) {
        this.cloudConfiguration = cloudConfiguration;
        this.line = line;
		this.time = time;
	}
	
	void addRgb(int rgb, int index) {
	    if (rgb == RainImage.RGB_TRANSPARENT_GIF_BLACK || rgb == RainImage.RGB_TRANSPARENT_GIF_WHITE) {
	    	setRain(index, false);	
	    } else {
	    	setRain(index, true);	
	    }
    }

	private void setRain(int index, boolean value) {
	    this.line[index] = value;
    }

    @Override
	public String toString() {
	    return getLineAsString() + " date: " + getTime();
	}
	
	public String getLineAsString() {
		StringBuilder result = new StringBuilder();
		for (Boolean isRain : this.line) {
			if (isRain) {
			    result.append(RAIN_SYMBOL);
			} else {
				result.append(SUN_SYMBOL);
			}
        }
		return result.toString();
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
			boolean[] startTruePattern = PatternBuilder.buildStartTrue(i + 1);
			boolean[] allFalsePattern = PatternBuilder.buildAllFalse(i + 1);
			replaceStart(startTruePattern, allFalsePattern);
			
			boolean[] startFalsePattern = PatternBuilder.buildStartFalse(i + 1);
			boolean[] allTruePattern = PatternBuilder.buildAllTrue(i + 1);
			replaceStart(startFalsePattern, allTruePattern);
		}
    }

	/**
	 * 
	 */
	private void smoothMiddle() {
		for (int i = cloudConfiguration.getReplaceHolesMin(); i <= cloudConfiguration.getReplaceHolesMax(); i++) {
			boolean[] middleFalsePattern = PatternBuilder.buildMiddleFalse(i + 2);
			boolean[] allTruePattern = PatternBuilder.buildAllTrue(i + 2);
			replacePattern(middleFalsePattern, allTruePattern);
			
			boolean[] middleTruePattern = PatternBuilder.buildMiddleTrue(i + 2);
			boolean[] allFalsePattern = PatternBuilder.buildAllFalse(i + 2);
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
    void replacePattern(boolean[] matchPattern, boolean[] replacePattern) {
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
        System.arraycopy(replacePattern, 0, this.line, index, replacePattern.length);
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
     * @return
     */
    private Distance getRainDistance(int fromIndex) {
        return getDistanceForValue(true, fromIndex);
    }

    /**
     * @return
     */
    private Distance getSunDistance(int fromIndex) {
        return getDistanceForValue(false, fromIndex);
    }

    /**
     * @return
     */
    public boolean isFutureRainDistanceInfinity() {
        return Distance.INFINITY.equals(getFutureRainDistance());
    }

    /**
     * @return
     */
    public boolean isFutureSunDistanceInfinity() {
        return Distance.INFINITY.equals(getFutureSunDistance());
    }

	/**
	 * @param value
	 * @return
	 */
    public Distance getDistanceForValue(boolean value) {
        return getDistanceForValue(value, 0);
    }

    public Distance getDistanceForValue(boolean value, int fromIndex) {
        for (int i = fromIndex; i < this.line.length; i++) {
            if (this.line[i] == value) {
                return new Distance(i);
            }
        }
        return Distance.INFINITY;
    }

    /**
     *
     * @return
     */
    public Distance getFutureRainDistance() {
        if (isSun()) {
            return getRainDistance();
        }
        Distance sunDistance = getSunDistance();
        if (Distance.INFINITY.equals(sunDistance)) {
            return Distance.INFINITY;
        }
        return getRainDistance(sunDistance.getValue());
    }

    /**
     *
     * @return
     */
    public Distance getFutureSunDistance() {
        if (isRain()) {
            return getSunDistance();
        }
        Distance rainDistance = getRainDistance();
        if (Distance.INFINITY.equals(rainDistance)) {
            return Distance.INFINITY;
        }
        return getSunDistance(rainDistance.getValue());
    }

    public boolean isRain() {
        if (line.length > 0) {
            return line[0];
        }
        throw new RuntimeException("Cloud line cannot hava zero length.");
    }

    public boolean isSun() {
        return !isRain();
    }


    public static boolean isRain(List<Cloud> clouds) {
        if (clouds.isEmpty()) {
            throw new RuntimeException("Cloud lines list cannot be empty.");
        }
        Cloud last = clouds.get(clouds.size() - 1);
        return last.isRain();
    }

    public static boolean isSun(List<Cloud> clouds) {
        return !isRain(clouds);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cloud cloud = (Cloud) o;
        return Arrays.equals(line, cloud.line) &&
                Objects.equals(time, cloud.time);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(time);
        result = 31 * result + Arrays.hashCode(line);
        return result;
    }
}
