package bg.com.bo.bff.providers.dtos.response.debit.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DCAccountsOrderMWResponse {
    @JsonProperty("data")
    private AccountsData data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class AccountsData {
        @JsonProperty("pciId")
        private Integer pciId;
    }
}
