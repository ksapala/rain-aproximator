/**
 * 
 */
package org.ksapala.rainaproximator.aproximation;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;

/**
 * @author krzysztof
 *
 */
public class AproximationResultWeatherDominanceComparator implements Comparator<AproximationResult> {

	/**
	 * 
	 */
	public AproximationResultWeatherDominanceComparator() {
	}

	@Override
	public int compare(AproximationResult result1, AproximationResult result2) {
		int weatherDominance1 = result1.getType().getWeatherDominance();
		int weatherDominance2 = result2.getType().getWeatherDominance();
		if (weatherDominance1 == weatherDominance2) {
			if (AproximationResultType.RAIN_AT_TIME.equals(result1.getType()) ||
					AproximationResultType.SUN_AT_TIME.equals(result1.getType())) {
				
				LocalDateTime predictTime1 = result1.getPredictTime();
                LocalDateTime predictTime2 = result2.getPredictTime();
				return predictTime1.compareTo(predictTime2);
			}
			return 0;
		}
		if (weatherDominance1 < weatherDominance2) {
			return 1;
		}
		return -1;
	}

}
