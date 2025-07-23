package bg.com.bo.bff.commons.annotations.generics;

import bg.com.bo.bff.commons.validators.generics.ReferenceCharsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReferenceCharsValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReferenceChars {
    String message() default "El texto contiene caracteres no permitidos.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}