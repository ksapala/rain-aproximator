package org.ksapala.rainaproximator;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ksapala.rainaproximator.aproximation.AproximationResultWeatherDominanceComparatorTest;
import org.ksapala.rainaproximator.aproximation.RainAproximatorTest;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLineTest;
import org.ksapala.rainaproximator.aproximation.cloud.CloudsOperationsTest;
import org.ksapala.rainaproximator.aproximation.regression.RegressionCalculatorTest;
import org.ksapala.rainaproximator.aproximation.regression.RegressionStateTest;
import org.ksapala.rainaproximator.aproximation.scan.LastRadarMapTimeParserTest;
import org.ksapala.rainaproximator.aproximation.scan.ScannerTest;
import org.ksapala.rainaproximator.aproximation.wind.WindGetterTest;
import org.ksapala.rainaproximator.utils.TimeUtilsTests;

@RunWith(Suite.class)
@SuiteClasses({
        CloudLineTest.class,
        CloudsOperationsTest.class,
        RegressionCalculatorTest.class,
        RegressionStateTest.class,
        LastRadarMapTimeParserTest.class,
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
