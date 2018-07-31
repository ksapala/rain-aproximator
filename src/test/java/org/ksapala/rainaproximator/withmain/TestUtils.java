package org.ksapala.rainaproximator.withmain;

import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.configuration.Configuration;

import java.time.LocalDateTime;

public class TestUtils {


    private final static LocalDateTime dummyTime = LocalDateTime.now();

    /**
     * @param stringCloudLine
     * @return
     */
    public static CloudLine stringToCloudLine(Configuration.Algorithm.Cloud cloudConfiguration, String stringCloudLine) {
        boolean[] line = stringToBoolean(stringCloudLine);
        CloudLine cloudLine = new CloudLine(cloudConfiguration, line, dummyTime);
        return cloudLine;
    }

    private static boolean[] stringToBoolean(String stringCloudLine) {
        boolean[] line = new boolean[stringCloudLine.length()];
        for (int i = 0; i < stringCloudLine.length(); i++) {
            char rainSymbol = stringCloudLine.charAt(i);
            String rainSymbolString = String.valueOf(rainSymbol);
            boolean isRain = CloudLine.RAIN_SYMBOL.equals(rainSymbolString);
            line[i] = isRain;
        }
        return line;
    }
}
