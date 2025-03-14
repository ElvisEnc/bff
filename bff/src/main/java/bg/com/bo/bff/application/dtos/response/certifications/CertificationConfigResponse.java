package bg.com.bo.bff.application.dtos.response.certifications;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationConfigResponse {

    @Schema(description = "Precio del certificado solicitado")
    private BigDecimal certPrice;

    @Schema(description = "Mensaje de respuesta")
    private String message;

}
