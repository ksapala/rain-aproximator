package org.ksapala.rainaproximator;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ksapala.rainaproximator.aproximation.RainAproximatorTest;
import org.ksapala.rainaproximator.aproximation.angle.AngleTest;
import org.ksapala.rainaproximator.aproximation.cloud.CloudTest;
import org.ksapala.rainaproximator.aproximation.cloud.CloudsOperationsTest;
import org.ksapala.rainaproximator.aproximation.regression.RegressionCalculatorTest;
import org.ksapala.rainaproximator.aproximation.result.ComparatorModeAccuracyTest;
import org.ksapala.rainaproximator.aproximation.result.ComparatorModePredictTimeTest;
import org.ksapala.rainaproximator.aproximation.scan.LastRadarMapTimeParserTest;
import org.ksapala.rainaproximator.aproximation.scan.ScannerTest;
import org.ksapala.rainaproximator.aproximation.weather.ConditionTest;
import org.ksapala.rainaproximator.aproximation.weather.WeatherTest;
import org.ksapala.rainaproximator.aproximation.wind.WindGetterTest;
import org.ksapala.rainaproximator.utils.TimeUtilsTests;

@RunWith(Suite.class)
@SuiteClasses({
        AngleTest.class,
        CloudTest.class,
        CloudsOperationsTest.class,
        ConditionTest.class,
        WeatherTest.class,
        LastRadarMapTimeParserTest.class,
        ScannerTest.class,
        WindGetterTest.class,
        RegressionCalculatorTest.class,
        ComparatorModePredictTimeTest.class,
        ComparatorModeAccuracyTest.class,
        RainAproximatorTest.class,
        TimeUtilsTests.class,
})
public class AllTests {

	@BeforeClass
    public static void setUp() {
    }
}
