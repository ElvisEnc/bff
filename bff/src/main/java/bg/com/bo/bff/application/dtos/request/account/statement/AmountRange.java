package bg.com.bo.bff.application.dtos.request.account.statement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountRange {
    @Schema(example = "0.5", description = "Monto mínimo")
    private Double min;

    @Schema(example = "1000", description = "Monto máximo")
    private Double max;
}
