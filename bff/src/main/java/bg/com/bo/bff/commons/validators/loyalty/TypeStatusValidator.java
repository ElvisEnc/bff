package bg.com.bo.bff.commons.validators.loyalty;

import bg.com.bo.bff.commons.annotations.loyalty.ValidTypeStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TypeStatusValidator implements ConstraintValidator<ValidTypeStatus, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "VIGENTE".equals(value) || "REDIMIDO".equals(value) || "VENCIDO".equals(value);
    }
}
