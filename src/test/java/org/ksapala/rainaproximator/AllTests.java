package org.ksapala.rainaproximator;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ksapala.rainaproximator.aproximation.AproximationResultWeatherDominanceComparatorTest;
import org.ksapala.rainaproximator.aproximation.RainAproximatorTest;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLineTest;
import org.ksapala.rainaproximator.aproximation.regression.RegressionCalculatorTest;
import org.ksapala.rainaproximator.aproximation.scan.LastRadarMapDateParserTest;
import org.ksapala.rainaproximator.aproximation.scan.ScannerTest;
import org.ksapala.rainaproximator.aproximation.wind.WindGetterTest;
import org.ksapala.rainaproximator.utils.TimeUtilsTests;

@RunWith(Suite.class)
@SuiteClasses({
        CloudLineTest.class,
        RegressionCalculatorTest.class,
        LastRadarMapDateParserTest.class,
        ScannerTest.class,
        WindGetterTest.class,
        AproximationResultWeatherDominanceComparatorTest.class,
        RainAproximatorTest.class,
        TimeUtilsTests.class,
})
public class AllTests {

	@BeforeClass
    public static void setUp() {
    }
}
