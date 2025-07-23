package bg.com.bo.bff.application.dtos.request.payment.service.affiliation;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record DependencyServiceAffiliation(
        @NotBlank
        @OnlyNumber
        @Schema(description = "Código del criterio seleccionado", example = "28")
        String code,

        @NotBlank
        @Schema(description = "Valor del criterio seleccionado", example = "73166120")
        String value
) {
}
