package org.ksapala.rainaproximator;

import org.ksapala.rainaproximator.aproximation.cloud.Cloud;
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
     * @param stringCloud
     * @return
     */
    public Cloud stringToCloud(String stringCloud) {
        boolean[] line = stringToBoolean(stringCloud);
        return new Cloud(configuration.getAlgorithm().getCloud(), line, dummyTime);
    }

    /**
     *
     * @param cloudString
     * @param timeString
     * @return
     */
    public Cloud cloud(String cloudString, String timeString) {
        Cloud cloud = stringToCloud(cloudString);
        cloud.setTime(parseInTest(timeString));
        return cloud;
    }

    /**
     *
     * @param stringCloud
     * @return
     */
    private static boolean[] stringToBoolean(String stringCloud) {
        boolean[] line = new boolean[stringCloud.length()];
        for (int i = 0; i < stringCloud.length(); i++) {
            char rainSymbol = stringCloud.charAt(i);
            String rainSymbolString = String.valueOf(rainSymbol);
            boolean isRain = Cloud.RAIN_SYMBOL.equals(rainSymbolString);
            line[i] = isRain;
        }
        return line;
    }

    /**
     *
     * @param timeString
     * @return
     */
    public LocalDateTime parseInTest(String timeString) {
        return TimeUtils.parseInTest(timeString, configuration);
    }
}
