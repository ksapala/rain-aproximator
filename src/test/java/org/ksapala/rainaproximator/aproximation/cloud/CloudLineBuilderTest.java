package org.ksapala.rainaproximator.aproximation.cloud;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ksapala.rainaproximator.exception.AproximationException;
import org.ksapala.rainaproximator.aproximation.map.LastRadarMapDateParser;
import org.ksapala.rainaproximator.settings.Property;
import org.ksapala.rainaproximator.settings.Settings;


public class CloudLineBuilderTest {

	private CloudLineBuilder cloudLineBuilder;
	private SimpleDateFormat format;
	
	@Before
	public void setUp() throws Exception {
		this.cloudLineBuilder = new CloudLineBuilder();
		this.format = new SimpleDateFormat(Settings.get(Property.LAST_SCAN_DATE_FORMAT));
	}

	@Test
	public final void testFillDates() throws ParseException, AproximationException {
		this.cloudLineBuilder.setLastRadarMapDateParser(new MockLastRadarMapDateParser());
		
		List<CloudLine> cloudLines = new ArrayList<CloudLine>();
		CloudLine cloudLine1 = CloudLine.fromString("....#.....####....");
		CloudLine cloudLine2 = CloudLine.fromString("....#...####......");
		CloudLine cloudLine3 = CloudLine.fromString("......####........");
		CloudLine cloudLine4 = CloudLine.fromString("....####..........");
		CloudLine cloudLine5 = CloudLine.fromString("..####............");
		
		cloudLines.add(cloudLine1);
		cloudLines.add(cloudLine2);
		cloudLines.add(cloudLine3);
		cloudLines.add(cloudLine4);
		cloudLines.add(cloudLine5);
		
		this.cloudLineBuilder.fillDates(cloudLines);
		
		assertEquals(this.format.parse("04/02/2015 19:30"), cloudLine1.getDate());
		assertEquals(this.format.parse("04/02/2015 19:40"), cloudLine2.getDate());
		assertEquals(this.format.parse("04/02/2015 19:50"), cloudLine3.getDate());
		assertEquals(this.format.parse("04/02/2015 20:00"), cloudLine4.getDate());
		assertEquals(this.format.parse("04/02/2015 20:10"), cloudLine5.getDate());

		
	}

	public class MockLastRadarMapDateParser extends LastRadarMapDateParser {

		@Override
        public Date readLastScanDateFromFile() {
			try {
	            Date date = CloudLineBuilderTest.this.format.parse("04/02/2015 20:10");
	            return date;
            } catch (ParseException e) {
	            e.printStackTrace();
            }
	        return null;
        }
		
	}
}
