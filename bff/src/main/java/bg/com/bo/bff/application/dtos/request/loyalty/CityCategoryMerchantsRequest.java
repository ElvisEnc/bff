package bg.com.bo.bff.application.dtos.request.loyalty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityCategoryMerchantsRequest {
    @NotBlank(message = "categoryId no puede ser vacío.")
    @Schema(description = "categoryId permite realizar la busqueda de comercios que pertenecen a esa categoria.",
            example = "520607aa-0fe0-4baf-b396-MNdsdbfaedc4a")
    private String categoryId;

    @NotBlank(message = "cityId no puede estar vacío.")
    @Schema(description = "cityId es necesario para realizar la busqueda de comercios en la ciudad con este ID.",
            example = "eff1726f-5ao0-4b24-b0be-5ujecefbe542")
    private String cityId;
}
