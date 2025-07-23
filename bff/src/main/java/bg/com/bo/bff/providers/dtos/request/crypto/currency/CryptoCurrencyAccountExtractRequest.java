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
public class CryptoCurrencyAccountExtractRequest {

    @JsonProperty("jtsOidNumber")
    private Integer jtsOidNumber;

    @JsonProperty("accountNumber")
    private String accountNumber;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("startPage")
    private int startPage;

    @JsonProperty("endPage")
    private int endPage;

}
