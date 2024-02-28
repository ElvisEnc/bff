package bg.com.bo.bff.providers.dtos.requests.login;

@lombok.Data
public class LoginMWRequest {
    private String schemeName;
    private String user;
    private String dniComplement;
    private String password;
    private String deviceId;
    private String deviceIp = "";
}
