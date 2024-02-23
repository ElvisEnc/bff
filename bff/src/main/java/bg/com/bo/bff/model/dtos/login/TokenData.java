package bg.com.bo.bff.model.dtos.login;

@lombok.Data
public class TokenData {
    private String accessToken;
    private String refreshToken;
}
