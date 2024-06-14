package bg.com.bo.bff.commons.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UtilDate {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String[] meses = {
            "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio",
            "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    };

    public static String formatDateLong(String dateString) {
        String[] parts = dateString.split(" ");
        String datePart = parts[0];

        String[] dateParts = datePart.split("/");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        if (year > 1000) {
            return formatDate(day, month, year);
        } else if (day > 31) {
            return formatDate(day, month, year);
        } else {
            return formatDate(month, day, year + 2000);
        }
    }

    private static String formatDate(int day, int month, int year) {
        String formattedMonth = meses[month - 1];
        return String.format("%d de %s, %d", day, formattedMonth, year);
    }

    public static String formatTime(String timeString) {
        String[] parts = timeString.split(":");
        String hour = parts[0];
        String minute = parts[1];
        return hour + ":" + minute;
    }

    public static boolean isDateOutOfDate(String dateStr) {
        try {
            LocalDate inputDate = LocalDate.parse(dateStr, DATE_FORMATTER);
            LocalDate currentDate = LocalDate.now();
            return !currentDate.isBefore(inputDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
        }
    }
}
