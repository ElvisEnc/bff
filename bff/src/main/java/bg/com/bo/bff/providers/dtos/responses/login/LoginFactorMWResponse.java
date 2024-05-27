package bg.com.bo.bff.providers.dtos.responses.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginFactorMWResponse {
    private LoginFactorData data;
}
