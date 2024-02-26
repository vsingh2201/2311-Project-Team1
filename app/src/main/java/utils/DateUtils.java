package utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

        public static String convertEpochToString(String epochString) {
            long epoch = Long.parseLong(epochString);
      
            Instant instant = Instant.ofEpochMilli(epoch);

            ZonedDateTime dateTime = instant.atZone(ZoneId.of("America/Toronto"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");

            return dateTime.format(formatter);
        }

        public static String convertEpochToStringWithoutHours(String epochString) {
            long epoch = Long.parseLong(epochString);
      
            Instant instant = Instant.ofEpochMilli(epoch);

            ZonedDateTime dateTime = instant.atZone(ZoneId.of("America/Toronto"));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");

            return dateTime.format(formatter);
        }

}