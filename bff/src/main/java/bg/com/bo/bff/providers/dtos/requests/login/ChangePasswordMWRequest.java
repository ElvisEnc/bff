package bg.com.bo.bff.providers.dtos.requests.login;

import lombok.Data;

@Data
public class ChangePasswordMWRequest {
    private String previousPassword;
    private String newPassword;
    private MWOwnerAccountRequest ownerAccount;
}
