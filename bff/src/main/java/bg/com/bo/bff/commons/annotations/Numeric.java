package bg.com.bo.bff.commons.annotations;

import bg.com.bo.bff.commons.validators.generics.NumericValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NumericValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Numeric {
    String message() default "El campo no puede ser nulo o vacío";

    String notNullMessage() default "El campo no puede ser nulo o vacío";

    String onlyNumberMessage() default "El campo no es un número válido";

    String greaterThanMessage() default "El campo debe ser igual o mayor a %s";

    String lowerThanMessage() default "El campo debe ser igual o menor a %s";

    String notValidNumberMessage() default "El campo no es un número válido para el tipo declarado";

    String fieldName() default "";

    String min() default "";

    String max() default "";

    Class<? extends Number> numericType();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
