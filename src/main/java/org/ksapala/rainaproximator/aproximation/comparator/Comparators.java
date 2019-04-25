/**
 * 
 */
package org.ksapala.rainaproximator.aproximation.comparator;

import org.ksapala.rainaproximator.aproximation.result.Aproximation;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

/**
 * @author krzysztof
 *
 */
public class Comparators {

    public static final int MIN_TO_BE_ACCURATE = 3;
    public static final int MIN_TO_BE_ACCEPTABLY_ACCURATE = 4;

    /**
	 *
	 */
	public Comparators() {
	}

    public static Comparator<Aproximation> modeAccuracy() {
        return Comparators.isAccurate()
                .thenComparing(Comparators.isAcceptablyAccurate())
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
                a -> a.getAccuracy().getPoinsCount() < MIN_TO_BE_ACCURATE && !Double.isNaN(a.getAccuracy().getRSquare()),
                Comparator.naturalOrder()
        );
    }

    private static Comparator<Aproximation> isAcceptablyAccurate() {
        return Comparator.comparing(
                a -> a.getAccuracy().getPoinsCount() < MIN_TO_BE_ACCEPTABLY_ACCURATE && !Double.isNaN(a.getAccuracy().getRSquare()),
                Comparator.naturalOrder()
        );
    }

    private static Comparator<Aproximation> byPredictTime() {
        return (a1, a2) ->
                a1.getAproximationResult().getTime().flatMap(t1 ->
                a2.getAproximationResult().getTime().flatMap(t2 ->
                Optional.of(t1.compareTo(t2))
        )).orElse(0);
    }

}
