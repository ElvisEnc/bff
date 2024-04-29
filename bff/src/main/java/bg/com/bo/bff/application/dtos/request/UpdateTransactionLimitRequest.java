package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionLimitRequest {
    @NotNull(message = "Invalid amount")
    @Min(value = 1, message = "Invalid amount")
    @Max(value = 10000, message = "Invalid amount")
    @Schema(example = "5000", description = "Monto disponible permitido por transacción.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer amountLimit;

    @NotNull(message = "Invalid countLimit")
    @Min(value = 1, message = "Invalid countLimit")
    @Schema(example = "10", description = "Cantidad de retiros por dia.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer countLimit;

}


