package bg.com.bo.bff.providers.dtos.request.loyalty;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoyaltyRegisterSubscriptionRequest {

    @JsonProperty("firmaDigital")
    private boolean signatureDigital;
    @JsonProperty("idPersona")
    private String idPerson;
    @JsonProperty("codigoCampana")
    private String codeCampaign;
    @JsonProperty("numeroCuentaJTS_OID")
    private String jtsOidAccountNumber;
    @JsonProperty("email")
    private String email;
    @JsonProperty("suscripcionOrigen")
    private String subscriptionOrigin;
}
