package org.ksapala.rainaproximator.utils;

import org.ksapala.rainaproximator.aproximation.regression.RegressionState;

public class LoggingUtils {

    public static void log(RegressionState regressionState) {
        regressionState.log();
    }

    public static String getTmeOrNan(double milis) {
        if (Double.isNaN(milis)) {
            return Double.toString(milis);
        }
        return TimeUtils.millisToLocalDateAndTime((long) milis).toString();
    }
}
