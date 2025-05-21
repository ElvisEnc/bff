package bg.com.bo.bff.commons.annotations.loyalty;

import bg.com.bo.bff.commons.validators.loyalty.TypeStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {TypeStatusValidator.class })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTypeStatus {
    String message() default "Tipo inválido. Solo se permiten los valores: VIGENTE, REDIMIDO, VENCIDO";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
