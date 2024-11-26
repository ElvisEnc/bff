package bg.com.bo.bff.commons.validators.credit.card;

import bg.com.bo.bff.commons.annotations.credit.card.ValidRequestType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TypeRequestValidator implements ConstraintValidator<ValidRequestType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "I".equals(value) || "L".equals(value);
    }
}
