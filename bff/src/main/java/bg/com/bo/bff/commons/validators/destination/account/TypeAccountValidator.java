package bg.com.bo.bff.commons.validators.destination.account;

import bg.com.bo.bff.commons.annotations.destination.account.ValidAccountType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TypeAccountValidator implements ConstraintValidator<ValidAccountType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "1".equals(value) || "2".equals(value) || "3".equals(value);
    }
}
