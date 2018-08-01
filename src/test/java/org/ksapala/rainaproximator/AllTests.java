package org.ksapala.rainaproximator;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLineTest;
import org.ksapala.rainaproximator.aproximation.regression.RegressionCalculatorTest;
import org.ksapala.rainaproximator.aproximation.scan.LastRadarMapDateParserTest;
import org.ksapala.rainaproximator.utils.UtilsTests;

@RunWith(Suite.class)
@SuiteClasses({
        UtilsTests.class,
        CloudLineTest.class,
        RegressionCalculatorTest.class,
        LastRadarMapDateParserTest.class})
public class AllTests {

	@BeforeClass
    public static void setUp() {
    }
}
