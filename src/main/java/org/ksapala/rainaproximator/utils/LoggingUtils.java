package org.ksapala.rainaproximator.utils;

import org.ksapala.rainaproximator.aproximation.weather.Condition;

public class LoggingUtils {

    public static void log(Condition regressionState) {
        regressionState.log();
    }

    public static String getTmeOrNan(double milis) {
        if (Double.isNaN(milis)) {
            return Double.toString(milis);
        }
        return TimeUtils.millisToLocalDateAndTime((long) milis).toString();
    }
}
