package bg.com.bo.bff.application.dtos.request.own.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateTransactionLimitRequest(
        @NotBlank(message = "Invalid amountLimit")
        @NotNull(message = "Invalid amountLimit")
        @Pattern(regexp = "^(?!0)([1-9]\\d{0,3}|10000)$", message = "Invalid amountLimit")
        @Schema(example = "5000", description = "Monto límite disponible permitido por transacción, hasta 5000", requiredMode = Schema.RequiredMode.REQUIRED)
        String amountLimit,

        @NotNull(message = "Invalid countLimit")
        @NotBlank(message = "Invalid countLimit")
        @Pattern(regexp = "^(0?[1-9]|[1-9]\\d)$", message = "Invalid countLimit")
        @Schema(example = "99", description = "Cantidad límite de transacciones permitidas por día, hasta 99", requiredMode = Schema.RequiredMode.REQUIRED)
        String countLimit
) {
}
