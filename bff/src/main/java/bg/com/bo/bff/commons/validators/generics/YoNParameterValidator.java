package bg.com.bo.bff.commons.validators.generics;

import bg.com.bo.bff.commons.annotations.generics.ValidYoNParameter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class YoNParameterValidator implements ConstraintValidator<ValidYoNParameter, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "S".equals(value) || "N".equals(value);
    }
}
