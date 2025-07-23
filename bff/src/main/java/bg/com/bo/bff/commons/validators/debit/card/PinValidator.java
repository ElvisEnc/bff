package bg.com.bo.bff.commons.validators.debit.card;

import bg.com.bo.bff.commons.annotations.debit.card.ValidPin;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PinValidator implements ConstraintValidator<ValidPin, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return String.valueOf(value).length() == 4;
    }
}
