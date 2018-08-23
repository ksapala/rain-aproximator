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
	 * @param directionalAproximationResults
	 * @return
	 */
	public static DirectionalAproximationResult merge(List<DirectionalAproximationResult> directionalAproximationResults) {
		Collections.sort(directionalAproximationResults, new AproximationResultWeatherDominanceComparator());
        DirectionalAproximationResult aproximationResult = directionalAproximationResults.get(0);
		return aproximationResult;
	}

}
