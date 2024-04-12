package bg.com.bo.bff.providers.dtos.requests.login;

@lombok.Data
public class MWOwnerAccountRequest {
    private String personId;
    private String userDeviceId;
    private String personRoleId;
}
