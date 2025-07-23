package bg.com.bo.bff.commons.annotations;

import bg.com.bo.bff.commons.validators.generics.DatePatternValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DatePatternValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DatePattern {
    String message() default "Formato de fecha inválida";
    String invalidFormat() default "Formato incorrecto, se espera 'yyyy-MM-dd'";
    String notNull() default "No puede ser nulo ni vacío";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
