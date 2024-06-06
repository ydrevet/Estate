package fr.openclassrooms.ydrevet.estate.services;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateFormatterService {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd").withZone(ZoneId.from(ZoneOffset.UTC));

}
