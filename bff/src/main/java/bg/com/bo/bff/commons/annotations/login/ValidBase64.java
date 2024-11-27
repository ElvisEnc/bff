package bg.com.bo.bff.commons.annotations.login;

import bg.com.bo.bff.commons.validators.login.Base64Validator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = Base64Validator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBase64 {
    String message() default "El parámetro es inválido, no tiene el formato Base64 codificado.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
