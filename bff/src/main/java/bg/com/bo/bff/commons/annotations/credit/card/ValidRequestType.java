package bg.com.bo.bff.commons.annotations.credit.card;

import bg.com.bo.bff.commons.validators.credit.card.TypeRequestValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {TypeRequestValidator.class })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRequestType {
    String message() default "Tipo inválido. Solo se permiten los valores: I, L";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
