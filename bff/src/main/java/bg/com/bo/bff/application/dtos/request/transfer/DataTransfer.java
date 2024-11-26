package bg.com.bo.bff.application.dtos.request.transfer;

import bg.com.bo.bff.commons.annotations.generics.DescriptionChars;
import bg.com.bo.bff.commons.annotations.generics.ValidYoNParameter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataTransfer {

    @DescriptionChars
    @NotBlank(message = "La descripción no puede estar vacía")
    @NotNull(message = "Descripción no válida")
    @Size(min = 3, max = 50, message = "La descripción debe tener entre 3 y 50 caracteres.")
    @Schema(description = "Descripción de la transferencia", example = "Pago de servicios")
    private String description;

    @DescriptionChars
    @Size(min = 25, max = 100, message = "La fuente de los fondos debe tener entre 25 y 100 caracteres.")
    @Schema(description = "Fuente de fondos para la transferencia", example = "Fuente de fondos para la transferencia")
    private String sourceOfFounds;

    @DescriptionChars
    @Size(min = 25, max = 100, message = "El destino de los fondos debe tener entre 25 y 100 caracteres.")
    @Schema(description = "Destino de los fondos para la transferencia", example = "Destino de los fondos para la transferencia")
    private String destinationOfFounds;

    @ValidYoNParameter
    @Schema(description = "Autorizar transferencia duplicada. (S/N)", example = "S")
    private String allowsDuplicate = "N";
}
