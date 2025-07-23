package bg.com.bo.bff.commons.annotations.generics;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@NotNull(message = "El monto es obligatorio")
@Digits(integer = 7, fraction = 2, message = "El monto debe tener hasta 7 dígitos enteros y 2 decimales")
@Max(value = 1000000, message = "El monto máximo permitido es 1,000,000.00")
@Schema(description = "Monto de la transferencia con máximo de 1,000,000", example = "999999.99")
public @interface ValidAmountQR {
    String message() default "Monto inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
