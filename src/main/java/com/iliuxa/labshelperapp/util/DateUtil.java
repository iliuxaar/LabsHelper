package com.iliuxa.labshelperapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateUtil {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    public static Date parse(String dateString) {
        try {
            return new SimpleDateFormat(DATE_PATTERN).parse(dateString);
        } catch (DateTimeParseException | ParseException e) {
            return null;
        }
    }

    public static boolean validDate(String dateString) {
        // Пытаемся разобрать строку.
        return DateUtil.parse(dateString) != null;
    }
}