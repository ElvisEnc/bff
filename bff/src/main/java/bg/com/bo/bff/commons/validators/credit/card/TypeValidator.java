package bg.com.bo.bff.commons.validators.credit.card;

import bg.com.bo.bff.commons.annotations.credit.card.ValidType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TypeValidator implements ConstraintValidator<ValidType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "H".equals(value) || "T".equals(value);
    }
}
