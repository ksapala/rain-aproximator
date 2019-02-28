/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.comparator;

import org.ksapala.rainaproximator.aproximation.result.DirectionalAproximationResult;

import java.util.Comparator;

/**
 * @author krzysztof
 *
 */
public class Comparators {

	/**
	 *
	 */
	public Comparators() {
	}

    public static Comparator<DirectionalAproximationResult> modeAccuracy() {
        return Comparators.byWeatherDominance().thenComparing(Comparators.byAccuracy());
    }

    public static Comparator<DirectionalAproximationResult> modePredictTime() {
        return Comparators.byWeatherDominance().thenComparing(Comparators.byPredictTime());
    }

    private static Comparator<DirectionalAproximationResult> byWeatherDominance() {
        return Comparator.comparing(
                d -> d.getAproximationResult().getType().getWeatherDominance()
        );
    }

    private static Comparator<DirectionalAproximationResult> byAccuracy() {
        return Comparator.comparing(
                d -> d.getAproximationResult().getAccuracy(),
                Comparator.naturalOrder()
        );
    }

    private static Comparator<DirectionalAproximationResult> byPredictTime() {
        return Comparator.comparing(
                d -> d.getAproximationResult().getPredictTime(),
                Comparator.naturalOrder()
        );
    }

}
