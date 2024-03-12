package bg.com.bo.bff.providers.dtos.requests.login;


import lombok.Builder;

@lombok.Data
@Builder
public class LoginMWFactorRequest {
    private String codeTypeAuthentication;
    private String factor;
    private String geoReference;
    private LoginMWFactorDeviceRequest deviceIdentification;
}
