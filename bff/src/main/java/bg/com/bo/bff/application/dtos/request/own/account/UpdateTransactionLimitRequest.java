package bg.com.bo.bff.application.dtos.request.own.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateTransactionLimitRequest(

        @NotBlank(message = "Invalid amountLimit, must not be blank")
        @NotNull(message = "Invalid amountLimit, must not be null")
        @Pattern(regexp = "^(?!0)([1-9]\\d{0,3}|5000)$", message = "Monto inválido, el valor debe ser entre 1 y 5000")
        @Schema(example = "5000", description = "Monto límite disponible permitido por transacción, valor entre 1 y 5000", requiredMode = Schema.RequiredMode.REQUIRED)
        String amountLimit,

        @NotBlank(message = "Invalid countLimit, must not be blank")
        @NotNull(message = "Invalid countLimit, must not be null")
        @Pattern(regexp = "^(0?[1-9]|[1-9]\\d)$", message = "Límite inválido, el valor de ser entre 1 y 99")
        @Schema(example = "99", description = "Cantidad límite de transacciones permitidas por día, valor entre 1 y 99", requiredMode = Schema.RequiredMode.REQUIRED)
        String countLimit
) {
}
