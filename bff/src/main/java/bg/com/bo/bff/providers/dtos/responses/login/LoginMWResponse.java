package bg.com.bo.bff.providers.dtos.responses.login;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginMWResponse {
    private String statusCode;
    private UserMWResponse data;
}
