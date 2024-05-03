package bg.com.bo.bff.application.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TransactionLimitData {

    @Schema(example = "5000", description = "Monto disponible permitido por transacción.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer amountLimit;

    @Schema(example = "10", description = "Cantidad de retiros por dia.", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer countLimit;
}
