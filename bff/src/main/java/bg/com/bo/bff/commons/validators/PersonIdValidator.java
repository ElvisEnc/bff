package bg.com.bo.bff.commons.validators;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersonIdValidator implements ConstraintValidator<OnlyNumber, String> {
    @Override
    public boolean isValid(String personId, ConstraintValidatorContext context) {
        if (personId == null || personId.trim().isEmpty()) {
            return false;
        }

        if (!personId.matches("\\d+")) {
            return false;
        }

        try {
            long value = Long.parseLong(personId);
            if (value <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return personId.length() <= 20;
    }
}
