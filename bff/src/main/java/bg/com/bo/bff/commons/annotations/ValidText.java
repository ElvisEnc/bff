package bg.com.bo.bff.commons.annotations;

import bg.com.bo.bff.commons.validators.generics.ValidTextValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidTextValidator.class)
public @interface ValidText {
    String message() default "El campo contiene caracteres no permitidos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
