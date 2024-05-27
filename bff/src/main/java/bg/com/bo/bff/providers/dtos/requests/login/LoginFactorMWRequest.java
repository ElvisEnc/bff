package bg.com.bo.bff.providers.dtos.requests.login;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginFactorMWRequest {
    private String codeTypeAuthentication;
    private String factor;
}
