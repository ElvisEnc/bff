package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.commons.annotations.generics.ReferenceChars;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReferenceCharsValidator implements ConstraintValidator<ReferenceChars, String> {

    private static final String VALID_PATTERN = "^[0-9a-zA-Z\\s&\\-_ÑñÁáÉéÍíÓóÚúÜü.]*$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.matches(VALID_PATTERN);
    }
}
