package bg.com.bo.bff.models;

@lombok.Data
public class TokenDataServiceResponse {
    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private Integer refreshExpiresIn;
}
