package bg.com.bo.bff.providers.dtos.request.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginCredentialMWRequest {
    private String personId;
    private String password;
    private String idGeneratorUuid;
    private String loginType;
    private String tokenFinger;
}
