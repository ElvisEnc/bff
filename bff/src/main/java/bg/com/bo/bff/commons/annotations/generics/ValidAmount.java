package bg.com.bo.bff.commons.annotations.generics;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@NotNull(message = "El monto es obligatorio")
@DecimalMin(value = "0.10", inclusive = false, message = "El monto debe ser mayor que cero")
@Digits(integer = 12, fraction = 2, message = "El monto debe tener hasta 12 dígitos enteros y 2 decimales")
@Schema(description = "Monto de la transferencia", example = "100.00")
public @interface ValidAmount {
    String message() default "Monto inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
