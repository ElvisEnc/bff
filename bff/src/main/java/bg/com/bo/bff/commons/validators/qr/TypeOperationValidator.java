package bg.com.bo.bff.commons.validators.qr;

import bg.com.bo.bff.commons.annotations.qr.ValidOperationType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TypeOperationValidator implements ConstraintValidator<ValidOperationType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "1".equals(value) || "2".equals(value);
    }
}
