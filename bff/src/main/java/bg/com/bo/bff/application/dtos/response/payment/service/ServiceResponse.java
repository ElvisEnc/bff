package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse {
    @Schema(example = "42", description = "Código del servicio")
    private String serviceCode;

    @Schema(example = "Entel", description = "Nombre del servicio")
    private String serviceName;

    @Schema(example = "1", description = "Id de la categoría")
    private String categoryId;

    @Schema(example = "Colegios", description = "Descripcion de la categoría")
    private String categoryDesc;

    @Schema(example = "16", description = "Id de la subcategoría")
    private String subCategoryId;

    @Schema(example = "Colegios", description = "Descripcion de la subcategoría")
    private String subCategoryDesc;
}
