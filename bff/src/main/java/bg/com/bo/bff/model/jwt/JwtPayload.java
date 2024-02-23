package bg.com.bo.bff.model.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@lombok.Data
@lombok.NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtPayload {
    @JsonProperty("exp")
    private String expiration;
    @JsonProperty("iat")
    private String issuedAt;
    @JsonProperty("jti")
    private String id;
    @JsonProperty("iss")
    private String issuer;
    @JsonProperty("aud")
    private String audience;
    @JsonProperty("sub")
    private String subject;
    @JsonProperty("typ")
    private String type;
    @JsonProperty("azp")
    private String authorizedParty;
    @JsonProperty("sid")
    private String sessionId;
    @JsonProperty("client_id")
    private String clientId;
    @JsonIgnore
    private List<String> roles;
    @JsonIgnore
    private String personId;
}
