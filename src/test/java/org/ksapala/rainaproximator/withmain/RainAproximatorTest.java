/**
 * 
 */
package org.ksapala.rainaproximator.withmain;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author krzysztof
 *
 */
public class RainAproximatorTest {

	private SimpleDateFormat format;

	/**
	 * 
	 */
	public RainAproximatorTest() {
//		System.setProperty("dataDir","./");
//		Settings.initDefaultSettings();
//		this.format = new SimpleDateFormat(Settings.get(Property.LAST_SCAN_DATE_FORMAT));
//		Settings.injectableSettings = new DeveloperSettings();
	}

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		RainAproximatorTest test = new RainAproximatorTest();
		test.testAproximate();
	}
	
	public void testAproximate() throws ParseException {
//		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
//		CloudLine cloudLine1 = CloudLine.fromString("#############################........#########");
//		CloudLine cloudLine2 = CloudLine.fromString("###################..............#############");
//		CloudLine cloudLine3 = CloudLine.fromString("###########...................################");
//		CloudLine cloudLine4 = CloudLine.fromString("######.....................#########.#########");
//		CloudLine cloudLine5 = CloudLine.fromString("......................########################");
//
//		cloudLine1.setDate(this.format.parse("04/02/2015 13:15"));
//		cloudLine2.setDate(this.format.parse("04/02/2015 13:25"));
//		cloudLine3.setDate(this.format.parse("04/02/2015 13:35"));
//		cloudLine4.setDate(this.format.parse("04/02/2015 13:45"));
//		cloudLine5.setDate(this.format.parse("04/02/2015 13:55"));
//
//
//		cloudLines.add(cloudLine1);
//		cloudLines.add(cloudLine2);
//		cloudLines.add(cloudLine3);
//		cloudLines.add(cloudLine4);
//		cloudLines.add(cloudLine5);
//
//		AproximationResult aproximationResult = new RainAproximator().aproximate(cloudLines);
//
//		System.out.println(aproximationResult.toFriendlyString());
	}

}
