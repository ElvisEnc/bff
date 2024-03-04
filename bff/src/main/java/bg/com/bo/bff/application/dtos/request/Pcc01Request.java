package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

@lombok.Data
@ToString
public class Pcc01Request {
    @Schema(example = "840", description = "Este es el código de la moneda")
    private String currency;

    @Schema(example = "10000", description = "Monto")
    private Double amount;
}
