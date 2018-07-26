package org.ksapala.rainaproximator.withmain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().test();
    }

    private void test() {

        LocalDateTime local = LocalDateTime.of(2018, 3, 10, 10, 0, 0);


        ZonedDateTime utc = ZonedDateTime.of(local, ZoneOffset.UTC);


        ZonedDateTime converted = utc.withZoneSameInstant(ZoneId.of("Europe/Warsaw"));

        System.out.println(converted);

        System.out.println(converted.toLocalDateTime());
    }
}
