package bg.com.bo.bff.commons.annotations.debit.card;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = { TypeValidator.class })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidType {
    String message() default "Estado inválido. Solo se permiten los valores: 0, 4, 5, 7.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
