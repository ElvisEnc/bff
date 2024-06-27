package bg.com.bo.bff.commons.annotations.debit.card;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardNumberValidator implements ConstraintValidator<ValidCardNumber, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        String card = String.valueOf(value);
        return card.length() == 16 && card.startsWith("4");
    }
}
