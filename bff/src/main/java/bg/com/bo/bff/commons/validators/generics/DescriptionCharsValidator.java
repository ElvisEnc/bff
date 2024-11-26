package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.commons.annotations.generics.DescriptionChars;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DescriptionCharsValidator implements ConstraintValidator<DescriptionChars, String> {

    private static final String VALID_PATTERN = "^[0-9a-zA-Z&\\-_ÑñÁáÉéÍíÓóÚúÜü\\s]*$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches(VALID_PATTERN);
    }
}
