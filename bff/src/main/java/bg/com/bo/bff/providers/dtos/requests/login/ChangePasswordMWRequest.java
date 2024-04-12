package bg.com.bo.bff.providers.dtos.requests.login;

@lombok.Data
public class ChangePasswordMWRequest {
    private String previousPassword;
    private String newPassword;
    private MWOwnerAccountRequest ownerAccount;
}
