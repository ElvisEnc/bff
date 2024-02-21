package bg.com.bo.bff.provider.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import bg.com.bo.bff.controllers.request.LoginBiometricRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "LoginBiometricRequestDTO")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginBiometricRequestDTO {

    @JsonProperty(value = "servicieID")
    private String serviceID;

    @JsonProperty(value = "pStrNombreUsuario")
    private String userName;

    @JsonProperty(value = "did")
    private String did;

    @JsonProperty(value = "rsid")
    private String rSid;

    @JsonProperty(value = "version")
    private String version;

    @JsonProperty(value = "pStrKsBga")
    private String ksBga;

    public static LoginBiometricRequestDTO toParse(LoginBiometricRequest loginBiometric) {
        return LoginBiometricRequestDTO.builder()
                .serviceID("GanaMovilWS$mtdLoginBiometrico")
                .userName(loginBiometric.getUserName())
                .did(loginBiometric.getDid())
                .rSid(loginBiometric.getRSid())
                .version(loginBiometric.getVersion())
                .ksBga(loginBiometric.getKsBga())
                .build();
    }
}
