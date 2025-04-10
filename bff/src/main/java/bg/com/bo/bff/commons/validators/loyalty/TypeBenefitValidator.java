package bg.com.bo.bff.commons.validators.loyalty;

import bg.com.bo.bff.commons.annotations.loyalty.ValidTypeBenefit;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TypeBenefitValidator implements ConstraintValidator<ValidTypeBenefit, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return "PRODUCTO".equals(value) || "CONSUMO".equals(value) || "DESCUENTO".equals(value) || "PASAJES".equals(value);
    }
}
