package bg.com.bo.bff.commons.validators;

import bg.com.bo.bff.commons.annotations.DatePattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatePatternValidator implements ConstraintValidator<DatePattern, String> {
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        if (!value.matches(DATE_PATTERN)) {
            return false;
        }

        try {
            LocalDate.parse(value, FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
