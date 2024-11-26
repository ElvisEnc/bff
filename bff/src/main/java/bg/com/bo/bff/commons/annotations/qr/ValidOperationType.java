package bg.com.bo.bff.commons.annotations.qr;

import bg.com.bo.bff.commons.validators.qr.TypeOperationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {TypeOperationValidator.class })
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOperationType {
    String message() default "Tipo inválido. Solo se permiten los valores: 1 y 2";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
