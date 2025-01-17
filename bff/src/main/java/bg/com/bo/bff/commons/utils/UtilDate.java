package bg.com.bo.bff.commons.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class UtilDate {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Logger LOGGER = LogManager.getLogger(UtilDate.class.getName());

    private static final String[] meses = {
            "enero", "febrero", "marzo", "abril", "mayo", "junio", "julio",
            "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    };

    public static String formatDate(String dateString) {
        if (dateString == null || dateString.isBlank()) {
            return null;
        }

        String localFormat = "dd/MM/yyyy";
        List<DateTimeFormatter> formatters = Arrays.asList(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern(localFormat),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                if (dateString.contains("T")) {
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, formatter);
                    return zonedDateTime.toLocalDate().format(DateTimeFormatter.ofPattern(localFormat));
                } else if (dateString.contains(" ")) {
                    LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                    return dateTime.toLocalDate().format(DateTimeFormatter.ofPattern(localFormat));
                } else {
                    LocalDate date = LocalDate.parse(dateString, formatter);
                    return date.format(DateTimeFormatter.ofPattern(localFormat));
                }
            } catch (DateTimeParseException e) {
                LOGGER.debug("Unable to parse date with format: %s".formatted(formatter.toString()), e);
            }
        }
        return dateString;
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

    public static DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public static String getDateGenericFormat(String dateString) {
        if (dateString == null || dateString.isBlank()) {
            return null;
        }
        String yyyyMMddPattern = "\\d{4}-\\d{2}-\\d{2}";
        if (dateString.matches(yyyyMMddPattern)) {
            return dateString; // Return as is
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, inputFormatter);
        return date.format(outputFormatter);
    }

    public static String adaptDateToMWFormat(String dateString) {
        DateTimeFormatter inputFormatter = getDateFormatter();
        LocalDate date = LocalDate.parse(dateString, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(outputFormatter);
    }
}
