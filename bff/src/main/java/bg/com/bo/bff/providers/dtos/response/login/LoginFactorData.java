package bg.com.bo.bff.providers.dtos.response.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginFactorData {
    private String personId;
    private String codeImage;
    private String secondFactor;
    private String secondFactorAuthentication;
    private String idGeneratorUuid;
}
