package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionLimitRequest {
    @NotBlank(message = "Invalid amount")
    @NotNull(message = "Invalid amount")
    @Pattern(regexp = "^(?!0)([1-9]\\d{0,3}|10000)$")
    @Schema(example = "5000", description = "Monto disponible permitido por transacción.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String amountLimit;

    @NotNull(message = "Invalid countLimit")
    @NotBlank(message = "Invalid countLimit")
    @Pattern(regexp = "^(0?[1-9]|[1-9]\\d)$")
    private String countLimit;

}


