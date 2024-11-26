package bg.com.bo.bff.application.dtos.response.login;

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
    private String name;
    private String fullName;
    private String lastConnectionDate;
    private Boolean keyChange;
    private String keyChangeMessage;

    public enum StatusCode {
        SUCCESS
    }
}
