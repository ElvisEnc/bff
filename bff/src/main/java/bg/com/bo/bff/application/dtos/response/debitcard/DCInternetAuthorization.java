package bg.com.bo.bff.application.dtos.response.debitcard;

import bg.com.bo.bff.application.dtos.request.debit.card.DCLimitsPeriod;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class DCInternetAuthorization {
    @Schema(example = "123445", description = "Este es el id del registro")
    private String id;
    @Schema(example = "200", description = "Este es el monto ")
    private String amount;
    @Schema( description = "Este es el periodo de validez")
    private DCLimitsPeriod period;
}
