package bg.com.bo.bff.model.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Singular;

import java.io.Serializable;
import java.util.List;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class KeyCloakKeyResponse implements Serializable {
    @JsonProperty("kid")
    private String id;
    @JsonProperty("kty")
    private String keyType;
    @JsonProperty("alg")
    private String algorithm;
    @JsonProperty("use")
    private String publicKeyUse;
    @JsonProperty("n")
    private String rsaPublicModulus;
    @JsonProperty("e")
    private String rsaPublicExponent;
    @Singular("")
    @JsonProperty("x5c")
    private List<String> x5cs;
    private String x5t;
    @JsonProperty("x5t#S256")
    private String x5ts256;
}
