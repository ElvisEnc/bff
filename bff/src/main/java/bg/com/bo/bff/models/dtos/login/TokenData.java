package bg.com.bo.bff.models.dtos.login;

@lombok.Data
public class TokenData {
    private String accessToken;
    private String refreshToken;
}
