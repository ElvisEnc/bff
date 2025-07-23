package bg.com.bo.bff.application.dtos.response.crypto.currency;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateResponse {

    @Schema(description = "Tipo de cambio para compra de moneda extranjera")
    private Double purchaseFxRate ;

    @Schema(description = "Tipo de cambio para venta de moneda extranjera")
    private Double saleFxRate ;

    @Schema(description = "Descripción adicional sobre el tipo de cambio")
    private String description;
}
