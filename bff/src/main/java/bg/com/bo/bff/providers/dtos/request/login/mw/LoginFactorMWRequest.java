package bg.com.bo.bff.providers.dtos.request.login.mw;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginFactorMWRequest {
    private String codeTypeAuthentication;
    private String factor;
}
