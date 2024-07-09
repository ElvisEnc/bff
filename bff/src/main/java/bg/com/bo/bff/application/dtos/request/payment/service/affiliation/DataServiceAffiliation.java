package bg.com.bo.bff.application.dtos.request.payment.service.affiliation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record DataServiceAffiliation(
        @NotBlank
        @Pattern(regexp = "^\\d+")
        @Schema(description = "Identificación", example = "1")
        String identify,

        @NotBlank
        @Schema(description = "Nombre del propietario", example = "S/N")
        String nameOwner,

        @NotBlank
        @Schema(description = "Code", example = "73166120")
        String code,

        @NotBlank
        @Schema(description = "Descripción", example = "desc")
        String description,

        @Schema(description = "Datos adicionales", example = "null")
        String additionalFact,

        @Valid
        List<DataRegisterServiceAffiliation> dataRegister
) {
}
