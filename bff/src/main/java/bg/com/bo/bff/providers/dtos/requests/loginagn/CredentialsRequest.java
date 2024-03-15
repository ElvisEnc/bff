package bg.com.bo.bff.providers.dtos.requests.loginagn;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class CredentialsRequest {
    @JsonProperty("codUsuario")
    private String personId;
    private String password;
    @JsonProperty("biometriaHabilitada")
    private String biometricStatus;
    @JsonProperty("tokenBiometria")
    private String biometricToken;
}
