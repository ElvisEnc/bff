package bg.com.bo.bff.providers.dtos.requests.login;

import lombok.Data;

@Data
public class MWOwnerAccountRequest {
    private String personId;
    private String userDeviceId;
    private String personRoleId;
}
