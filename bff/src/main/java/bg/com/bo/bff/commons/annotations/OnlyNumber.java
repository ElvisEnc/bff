package bg.com.bo.bff.commons.annotations;

import bg.com.bo.bff.commons.validators.generics.PersonIdValidator;
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
    String message() default "Se espera solo números y menor a 18 dígitos";
    String notNullMessage() default "El campo no puede ser nulo o vacío";
    String onlyNumberMessage() default "El campo debe contener solo números";
    String greaterThanZeroMessage() default "El valor debe ser mayor a cero";
    String maxLengthMessage() default "El campo debe tener menos de 18 dígitos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
