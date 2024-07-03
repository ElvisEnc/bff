package bg.com.bo.bff.application.dtos.response.login;

@lombok.Data
public class TokenData {
    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private Integer refreshExpiresIn;
}
