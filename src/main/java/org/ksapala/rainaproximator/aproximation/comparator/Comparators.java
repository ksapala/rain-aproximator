/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.comparator;

import org.ksapala.rainaproximator.aproximation.result.Aproximation;

import java.time.LocalDateTime;
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
        return Comparators.isAccurate()
                .thenComparing(Comparators.byWeatherDominance())
                .thenComparing(Comparators.byAccuracy())
                .thenComparing(Comparators.byPredictTime());
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

    private static Comparator<Aproximation> isAccurate() {
        return Comparator.comparing(
                a -> a.getAccuracy().getPoinsCount() < 3 && !Double.isNaN(a.getAccuracy().getRSquare()),
                Comparator.naturalOrder()
        );
    }

    private static Comparator<Aproximation> byPredictTime() {
        return (a1, a2) -> {
            LocalDateTime predictTime1 = a1.getAproximationResult().getPredictTime();
            LocalDateTime predictTime2 = a2.getAproximationResult().getPredictTime();
            if (predictTime1 != null && predictTime2 != null) {
                return predictTime1.compareTo(predictTime2);
            }
            return 0;
        };
    }

}
