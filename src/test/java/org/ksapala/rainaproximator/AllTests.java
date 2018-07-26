package org.ksapala.rainaproximator;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.ksapala.rainaproximator.aproximation.RainAproximatorTest;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLineTest;
import org.ksapala.rainaproximator.aproximation.cloud.CloudLineBuilderTest;
import org.ksapala.rainaproximator.aproximation.map.LastRadarMapDateParserTest;
import org.ksapala.rainaproximator.aproximation.regression.RegressionCalculatorTest;
import org.ksapala.rainaproximator.aproximation.wind.WindGetterTest;
import org.ksapala.rainaproximator.settings.Settings;

@RunWith(Suite.class)
@SuiteClasses({ CloudLineBuilderTest.class, LastRadarMapDateParserTest.class, RainAproximatorTest.class,
        WindGetterTest.class, CloudLineTest.class, RegressionCalculatorTest.class})
public class AllTests {

	@BeforeClass
    public static void setUp() {
		Settings.initDefaultSettings();
    }
}
