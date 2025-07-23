package bg.com.bo.bff.commons.annotations.destination.account;

import bg.com.bo.bff.commons.validators.destination.account.TypeAccountValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = { TypeAccountValidator.class })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAccountType {
    String message() default "Estado inválido. Solo se permiten los valores: 1, 2 y 3.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
