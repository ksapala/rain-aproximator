package org.ksapala.rainaproximator;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.ksapala.rainaproximator.settings.Property;
import org.ksapala.rainaproximator.settings.Settings;

public class BaseTest {


	protected SimpleDateFormat format;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
    public void setUp() {
		System.setProperty("dataDir","./");
		Settings.initDefaultSettings();
		this.format = new SimpleDateFormat(Settings.get(Property.LAST_SCAN_DATE_FORMAT));
    }
	
	
	public BaseTest() {
	}

}
