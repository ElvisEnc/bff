package bg.com.bo.bff.commons.annotations.debit.card;

import bg.com.bo.bff.commons.validators.debit.card.CardNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardNumberValidator.class)
public @interface ValidCardNumber {
    String message() default "El número de la tarjeta debe ser de 16 dígitos y con el formato correcto";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
