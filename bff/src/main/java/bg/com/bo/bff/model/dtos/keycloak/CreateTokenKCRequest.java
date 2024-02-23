package bg.com.bo.bff.model.dtos.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@lombok.NoArgsConstructor
public class CreateTokenKCRequest {
    @JsonProperty("grant_type")
    private String grantType;
    @JsonProperty("claim_token")
    private String claimToken;
    @JsonProperty("claim_token_format")
    private String claimTokenFormat;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;
    @JsonProperty("audience")
    private String audience;
}
