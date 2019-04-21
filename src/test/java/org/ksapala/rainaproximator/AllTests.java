package org.ksapala.rainaproximator;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ksapala.rainaproximator.aproximation.RainAproximatorTest;
import org.ksapala.rainaproximator.aproximation.angle.AngleTest;
import org.ksapala.rainaproximator.aproximation.cloud.CloudTest;
import org.ksapala.rainaproximator.aproximation.cloud.CloudsOperationsTest;
import org.ksapala.rainaproximator.aproximation.domainfilters.FiltersTest;
import org.ksapala.rainaproximator.aproximation.regression.RegressionCalculatorTest;
import org.ksapala.rainaproximator.aproximation.comparator.ComparatorModeAccuracyTest;
import org.ksapala.rainaproximator.aproximation.comparator.ComparatorModePredictTimeTest;
import org.ksapala.rainaproximator.aproximation.result.AccuracyTest;
import org.ksapala.rainaproximator.aproximation.scan.LastRadarMapTimeParserTest;
import org.ksapala.rainaproximator.aproximation.scan.ScannerTest;
import org.ksapala.rainaproximator.aproximation.weather.ConditionTest;
import org.ksapala.rainaproximator.aproximation.weather.WeatherTest;
import org.ksapala.rainaproximator.aproximation.wind.WindGetterTest;
import org.ksapala.rainaproximator.rest.service.FirebaseServiceTest;
import org.ksapala.rainaproximator.utils.TimeUtilsTests;

@RunWith(Suite.class)
@SuiteClasses({
        AngleTest.class,
        CloudsOperationsTest.class,
        CloudTest.class,
        ComparatorModeAccuracyTest.class,
        ComparatorModePredictTimeTest.class,
        FiltersTest.class,
        RegressionCalculatorTest.class,
        AccuracyTest.class,
        LastRadarMapTimeParserTest.class,
        ScannerTest.class,
        ConditionTest.class,
        WeatherTest.class,
        WindGetterTest.class,
        RainAproximatorTest.class,
        FirebaseServiceTest.class,
        TimeUtilsTests.class,
})
public class AllTests {

	@BeforeClass
    public static void setUp() {
    }
}
