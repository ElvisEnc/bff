package bg.com.bo.bff.providers.dtos.request.login.mw;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MWOwnerAccountRequest {
    private String personId;
    private String userDeviceId;
    private String personRoleId;
}
