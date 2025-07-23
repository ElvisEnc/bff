package bg.com.bo.bff.commons.annotations.debit.card;

import bg.com.bo.bff.commons.validators.debit.card.PinValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PinValidator.class)
public @interface ValidPin {
    String message() default "El pin debe tener exactamente 4 dígitos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
