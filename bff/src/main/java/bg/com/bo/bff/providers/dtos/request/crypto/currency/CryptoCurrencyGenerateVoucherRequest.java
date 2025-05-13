package bg.com.bo.bff.providers.dtos.request.crypto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrencyGenerateVoucherRequest {

    @JsonProperty("seatNumber")
    private Long seatNumber;

    @JsonProperty("branch")
    private int branch;

    @JsonProperty("dateProcess")
    private String dateProcess;

}
