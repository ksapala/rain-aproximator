/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.result;

import org.ksapala.rainaproximator.aproximation.comparator.Comparators;
import org.ksapala.rainaproximator.configuration.Mode;

import java.util.Comparator;
import java.util.List;

/**
 * @author krzysztof
 *
 */
public class AproximationResultMerger {

	private Comparator<DirectionalAproximationResult> comparator;

    /**
     *
     * @param mode
     */
    public AproximationResultMerger(Mode mode) {
        if (Mode.COMPARE_TIME.equals(mode.getCompare())) {
            comparator = Comparators.modePredictTime();
        } else {
            comparator = Comparators.modeAccuracy();
        }
    }

	/**
	 * @param directionalAproximationResults
	 * @return
	 */
	public DirectionalAproximationResult merge(List<DirectionalAproximationResult> directionalAproximationResults) {
        directionalAproximationResults.sort(this.comparator);
        return directionalAproximationResults.get(0);
	}

}
