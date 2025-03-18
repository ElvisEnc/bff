package bg.com.bo.bff.application.dtos.response.certifications;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationTypesResponse {

    @Schema(description = "Codigo de la solicitud")
    private Integer requestCode;

    @Schema(description = "Tipo de codigo de certificado")
    private Integer typeCode;

    @Schema(description = "Descripcion del certificado")
    private String description;

}
