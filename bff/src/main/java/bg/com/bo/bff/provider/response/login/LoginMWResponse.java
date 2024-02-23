package bg.com.bo.bff.provider.response.login;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginMWResponse {
    private String statusCode;
    private UserMWResponse data;
}
