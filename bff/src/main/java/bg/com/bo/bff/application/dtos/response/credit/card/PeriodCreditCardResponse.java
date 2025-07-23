package bg.com.bo.bff.application.dtos.response.credit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodCreditCardResponse {
    @Schema(description = "código del periodo")
    private Integer periodCode;

    @Schema(description = "mes")
    private String month;

    @Schema(description = "año")
    private String year;

    @Schema(description = "fecha inicio")
    private String init;

    @Schema(description = "fecha fin")
    private String end;
}
