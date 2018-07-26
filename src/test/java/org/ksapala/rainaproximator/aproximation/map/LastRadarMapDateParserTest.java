package org.ksapala.rainaproximator.aproximation.map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ksapala.rainaproximator.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LastRadarMapDateParserTest {


    @Autowired
    private Configuration configuration;

	private LastRadarMapDateParser lastRadarMapDateParser;


	@Before
	public void setUp() {
		this.lastRadarMapDateParser = new LastRadarMapDateParser(configuration.getScanner());
	}
	
	@Test
	public void testDoParseLastScanDate() throws IOException, ParseException {
		LocalDateTime lastRadarMapDate = this.lastRadarMapDateParser.doParseLastRadarMapDate();
		
		assertNotNull(lastRadarMapDate);
		System.out.println("lastRadarMapDate: " + lastRadarMapDate);
	}

}
