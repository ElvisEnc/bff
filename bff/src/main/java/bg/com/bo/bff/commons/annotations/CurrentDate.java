package bg.com.bo.bff.commons.annotations;

import bg.com.bo.bff.commons.validators.generics.CurrentDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CurrentDateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentDate {
    String message() default "La fecha debe ser la fecha actual";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
