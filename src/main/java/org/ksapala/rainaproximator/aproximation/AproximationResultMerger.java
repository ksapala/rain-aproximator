/**
 * 
 */
package org.ksapala.rainaproximator.aproximation;

import java.util.Collections;
import java.util.List;

/**
 * @author krzysztof
 *
 */
public class AproximationResultMerger {

	/**
	 * 
	 */
	public AproximationResultMerger() {
	}
	
	/**
	 * @param aproximationResults
	 * @return
	 */
	public static AproximationResult merge(List<AproximationResult> aproximationResults) {
		Collections.sort(aproximationResults, new AproximationResultWeatherDominanceComparator());
		AproximationResult aproximationResult = aproximationResults.get(0);
		return aproximationResult;
	}

}
