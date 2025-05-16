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
public class AvailableBalanceResponse {

    @Schema(description = "currency")
    private String currency;

    @Schema(description = "availableBalance")
    private Double availableBalance;

    @Schema(description = "account")
    private Integer account;

    @Schema(description = "status")
    private String status;

    @Schema(description = "jtsOid")
    private Integer jtsOid;

    @Schema(description = "product")
    private String product;

}
