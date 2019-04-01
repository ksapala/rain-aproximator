/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.comparator;

import org.ksapala.rainaproximator.aproximation.result.Aproximation;

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

    public static Comparator<Aproximation> modeAccuracy() {
        return Comparators.byWeatherDominance().thenComparing(Comparators.byAccuracy());
    }

    public static Comparator<Aproximation> modePredictTime() {
        return Comparators.byWeatherDominance().thenComparing(Comparators.byPredictTime());
    }

    private static Comparator<Aproximation> byWeatherDominance() {
        return Comparator.comparing(
                d -> d.getAproximationResult().getType().getWeatherDominance()
        );
    }

    private static Comparator<Aproximation> byAccuracy() {
        return Comparator.comparing(
                Aproximation::getAccuracy,
                Comparator.naturalOrder()
        );
    }

    private static Comparator<Aproximation> byPredictTime() {
        return Comparator.comparing(
                a -> a.getAproximationResult().getPredictTime(),
                Comparator.naturalOrder()
        );
    }

}
