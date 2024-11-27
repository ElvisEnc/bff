package bg.com.bo.bff.commons.validators.login;

import bg.com.bo.bff.commons.annotations.login.ValidBase64;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

import java.util.Base64;

@AllArgsConstructor
public class Base64Validator implements ConstraintValidator<ValidBase64, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            Base64.getDecoder().decode(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
