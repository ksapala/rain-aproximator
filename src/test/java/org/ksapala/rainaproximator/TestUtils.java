package org.ksapala.rainaproximator;

import org.ksapala.rainaproximator.aproximation.cloud.CloudLine;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.ksapala.rainaproximator.utils.TimeUtils;

import java.time.LocalDateTime;

public class TestUtils {


    private final static LocalDateTime dummyTime = LocalDateTime.now();

    private Configuration configuration;

    public TestUtils(Configuration configuration) {
        this.configuration = configuration;
    }
    /**
     * @param stringCloudLine
     * @return
     */
    public CloudLine stringToCloudLine(String stringCloudLine) {
        boolean[] line = stringToBoolean(stringCloudLine);
        CloudLine cloudLine = new CloudLine(configuration.getAlgorithm().getCloud(), line, dummyTime);
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

    public LocalDateTime parseInTest(String timeString) {
        return TimeUtils.parseInTest(timeString, configuration);
    }
}
