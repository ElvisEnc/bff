package bg.com.bo.bff.application.dtos.request.payment.service.affiliation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DataRegisterServiceAffiliation(
        @NotBlank
        @Pattern(regexp = "[a-zA-Z0-9]+")
        @Schema(description = "Etiqueta", example = "cuenta")
        String label,

        @Schema(description = "Valor de la etiqueta", example = "73166120")
        String value,

        @Schema(description = "Mandatorio", example = "null")
        String mandatory,

        @Schema(description = "Editable", example = "null")
        String edit,

        @Schema(description = "Grupo", example = "null")
        String group,

        @Schema(description = "Descripción", example = "null")
        String description,

        @Schema(description = "Código", example = "null")
        String code
) {
}
