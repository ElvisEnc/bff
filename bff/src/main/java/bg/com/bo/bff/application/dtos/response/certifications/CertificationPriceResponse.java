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
public class CertificationPriceResponse {

    @Schema(description = "Identificador del cargo")
    private Integer chargeFeeId;

    @Schema(description = "Identificador del evento")
    private Integer eventId;

    @Schema(description = "Monto que se debe pagar")
    private Integer amount;

    @Schema(description = "Descripcion de la moneda")
    private String currencyDes;

    @Schema(description = "Codigo de la moneda")
    private Integer currencyCode;

    @Schema(description = "Rango desde")
    private Integer rangeFrom;

    @Schema(description = "Rango hasta")
    private Integer rangeTo;

    @Schema(description = "Tipo de rango")
    private String rangeType;

}
