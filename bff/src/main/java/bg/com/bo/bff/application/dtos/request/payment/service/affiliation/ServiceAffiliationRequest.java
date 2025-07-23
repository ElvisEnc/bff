package bg.com.bo.bff.application.dtos.request.payment.service.affiliation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record ServiceAffiliationRequest(
        @NotBlank
        @Pattern(regexp = "^\\d+")
        @Schema(description = "Código de servicio", example = "42")
        String serviceCode,

        @NotBlank
        @Schema(description = "Id del criterio", example = "24")
        String criteriaId,

        @NotBlank
        @Size(min = 3, max = 99)
        @Schema(description = "Nombre de la referencia", example = "referencia")
        String referenceName,

        @NotBlank
        @Pattern(regexp = "^\\d+")
        @Size(max = 4, min = 4)
        @Schema(description = "Año del la busqueda del criterio", example = "2024")
        String year,

        @NotBlank
        @Pattern(regexp = "^\\d+")
        @Schema(description = "Número de cuenta puede ser 0", example = "0")
        String accountNumber,

        @NotNull
        @Schema(description = "Valor de si es temporal", example = "false")
        Boolean isTemporal,

        @Valid
        @NotNull
        List<DependencyServiceAffiliation> searchFields,

        @Valid
        @NotNull
        List<DataServiceAffiliation> dataAffiliation
) {
}
