package bg.com.bo.bff.providers.dtos.response.credit.card.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationCreditCardMWResponse {

    @JsonProperty("codError")
    private String codError;
    @JsonProperty("desError")
    private String desError;
    @JsonProperty("amount")
    private String amount;
}
