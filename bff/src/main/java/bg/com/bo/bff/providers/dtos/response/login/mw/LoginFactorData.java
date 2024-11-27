package bg.com.bo.bff.providers.dtos.response.login.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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
