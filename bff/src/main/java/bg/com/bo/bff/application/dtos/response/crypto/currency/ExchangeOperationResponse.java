package bg.com.bo.bff.application.dtos.response.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeOperationResponse {

    @JsonProperty("importDebited")
    private Double importDebited;

    @JsonProperty("currency")
    private int currency;

    @JsonProperty("seatNo")
    private Long seatNo;

    @JsonProperty("receiptId")
    private int receiptId;

    @JsonProperty("tcCredit")
    private int tcCredit;

    @JsonProperty("tcDebit")
    private Double tcDebit;

    @JsonProperty("branch")
    private int branch;

    @JsonProperty("dateSeat")
    private String dateSeat;

    @JsonProperty("importItf")
    private Double importItf;

    @JsonProperty("importAccredited")
    private Double importAccredited;
}
