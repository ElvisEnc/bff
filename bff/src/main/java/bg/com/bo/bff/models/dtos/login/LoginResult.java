package bg.com.bo.bff.models.dtos.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
    private String personId;
    private TokenData tokenData;
    private StatusCode statusCode;
    private String userDeviceId;
    private String rolePersonId;

    public enum StatusCode {
        SUCCESS
    }
}
