package bg.com.bo.bff.application.dtos.request.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AffiliationDebtsRequest(
        @NotNull
        @Min(1)
        @Schema(example = "46521", description = "Código de servicio")
        Integer serviceCode,

        @NotNull
        @Min(1)
        @Schema(example = "2", description = "Código de concepto")
        Integer concept,

        @NotNull
        @Schema(example = "2024", description = "Año de la gestión")
        Integer year
) {
}
