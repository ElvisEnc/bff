package bg.com.bo.bff.commons.validators;

import bg.com.bo.bff.commons.annotations.DatePattern;
import bg.com.bo.bff.commons.utils.Util;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatePatternValidator implements ConstraintValidator<DatePattern, String> {
    private String invalidFormat;
    private String notNull;
    private static final DateTimeFormatter FORMATTER = Util.getDateFormatter();

    @Override
    public void initialize(DatePattern constraintAnnotation) {
        this.invalidFormat = constraintAnnotation.invalidFormat();
        this.notNull = constraintAnnotation.notNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(notNull)
                    .addConstraintViolation();
            return false;
        }
        try {
            LocalDate.parse(value, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(invalidFormat)
                    .addConstraintViolation();
            return false;
        }
    }
}
