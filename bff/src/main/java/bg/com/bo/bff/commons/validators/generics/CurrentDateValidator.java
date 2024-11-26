package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.commons.annotations.CurrentDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CurrentDateValidator implements ConstraintValidator<CurrentDate, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        try {
            LocalDate inputDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate currentDate = LocalDate.now();
            return inputDate.equals(currentDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
