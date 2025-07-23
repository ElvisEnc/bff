package bg.com.bo.bff.application.dtos.response.payment.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliatedServicesResponse {

    @Schema(description = "Código de categoría")
    private int categoryId;

    @Schema(description = "Código de subcategoría")
    private int subCategoryId;

    @Schema(description = "Nombre de subcategoría")
    private String subCategoryName;

    @Schema(description = "Código de servicio")
    private String serviceCode;

    @Schema(description = "nombre del servicio")
    private String serviceName;

    @Schema(description = "Lista de servicios afiliados")
    private List<AffiliatedService> data;

    @Schema(description = "Registro total de afiliados por servicio")
    private int total;
}
