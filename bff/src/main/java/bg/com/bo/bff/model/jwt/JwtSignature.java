package bg.com.bo.bff.model.jwt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@lombok.NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtSignature {
    @JsonProperty("e")
    private String rsaPublicExponent;
    @JsonProperty("kty")
    private String keyType;
    @JsonProperty("n")
    private String rsaPublicModulus;
}