package bg.com.bo.bff.model.dtos.login;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
    private String personId;
    private TokenData tokenData;
    private StatusCode statusCode;

    public enum StatusCode {
        SUCCESS
    }
}
