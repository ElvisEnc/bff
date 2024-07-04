package bg.com.bo.bff.application.dtos.request.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DebtsRequest(
        @NotNull
        @Min(1)
        @Schema(example = "46521", description = "Código de servicio")
        Integer serviceCode,

        @NotNull
        @Max(9999)
        @Schema(example = "2024", description = "Año de la gestión")
        Integer year
) {
}
