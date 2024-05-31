package bg.com.bo.bff.providers.dtos.request.login;

import lombok.Data;

@Data
public class MWOwnerAccountRequest {
    private String personId;
    private String userDeviceId;
    private String personRoleId;
}
