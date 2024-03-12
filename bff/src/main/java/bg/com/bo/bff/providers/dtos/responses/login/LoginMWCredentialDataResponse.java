package bg.com.bo.bff.providers.dtos.responses.login;

@lombok.Data
public class LoginMWCredentialDataResponse {
    private String securityImage;
    private String secondFactor;
    private String codError;
    private String desError;
}
