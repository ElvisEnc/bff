package bg.com.bo.bff.commons.annotations;

import bg.com.bo.bff.commons.validators.PersonIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PersonIdValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyNumber {
    String message() default "Se espera solo números y menor a 20 dígitos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
