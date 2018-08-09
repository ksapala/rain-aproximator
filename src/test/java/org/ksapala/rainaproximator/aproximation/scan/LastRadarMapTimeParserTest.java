package org.ksapala.rainaproximator.aproximation.scan;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LastRadarMapTimeParserTest {


    @Autowired
	private LastRadarMapTimeParser lastRadarMapTimeParser;

    @MockBean
    private Scanner scanner;

	@Before
	public void setUp() {
	}
	
	@Test
	public void testDoParseLastScanDate() throws IOException {
		LocalDateTime lastRadarMapDate = this.lastRadarMapTimeParser.parseLastRadarMapTime();
		
		assertNotNull(lastRadarMapDate);
		System.out.println("lastRadarMapDate: " + lastRadarMapDate);
	}

}
