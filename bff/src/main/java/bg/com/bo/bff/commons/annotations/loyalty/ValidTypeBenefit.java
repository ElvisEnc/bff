package bg.com.bo.bff.commons.annotations.loyalty;

import bg.com.bo.bff.commons.validators.loyalty.TypeBenefitValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {TypeBenefitValidator.class })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTypeBenefit {
    String message() default "Tipo inválido. Solo se permiten los valores: PRODUCTO, CONSUMO, DESCUENTO o PASAJES";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
