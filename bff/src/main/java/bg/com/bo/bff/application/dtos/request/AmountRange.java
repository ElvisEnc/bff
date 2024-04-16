package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;

@lombok.Data
public class AmountRange {
    @Schema(example = "0.5", description = "Monto mínimo")
    private Double min;

    @Schema(example = "1000", description = "Monto máximo")
    private Double max;
}
