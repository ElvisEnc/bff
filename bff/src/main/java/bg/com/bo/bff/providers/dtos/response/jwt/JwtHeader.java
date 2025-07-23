package bg.com.bo.bff.providers.dtos.response.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@lombok.NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtHeader {
    @JsonProperty("kid")
    private String id;
    @JsonProperty("alg")
    private String algorithm;
    @JsonProperty("typ")
    private String tokenType;
}
