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
public class CertificationPrefExchRateResponse {

    @Schema(description = "Tipo de cambio para compra de UFV")
    private String buyRateUFV;

    @Schema(description = "Tipo de cambio para compra de Euros")
    private String buyRateEUR;

    @Schema(description = "Tipo de cambio para venta de Euros")
    private String sellRateEur;

    @Schema(description = "Tipo de cambio para compra de Dolares")
    private String buyRate;

    @Schema(description = "Tipo de cambio para venta de Dolares")
    private String sellRate;

    @Schema(description = "Cliente")
    private String client;

}
