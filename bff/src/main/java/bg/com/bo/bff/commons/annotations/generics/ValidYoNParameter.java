package bg.com.bo.bff.commons.annotations.generics;

import bg.com.bo.bff.commons.validators.generics.YoNParameterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = { YoNParameterValidator.class })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidYoNParameter {
    String message() default "Parámetro inválido. Solo se permiten los valores: S y N.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
