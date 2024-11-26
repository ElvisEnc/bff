package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.commons.annotations.ValidText;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTextValidator implements ConstraintValidator<ValidText, String> {
    private static final String VALID_PATTERN = "^[0-9a-zA-ZÑñÁáÉéÍíÓóÚúÜü.,+\\-\\s]*$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches(VALID_PATTERN);
    }
}
