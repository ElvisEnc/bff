package bg.com.bo.bff.providers.dtos.request.login.mw;

import lombok.Data;

@Data
public class ChangePasswordMWRequest {
    private String previousPassword;
    private String newPassword;
    private MWOwnerAccountRequest ownerAccount;
}
