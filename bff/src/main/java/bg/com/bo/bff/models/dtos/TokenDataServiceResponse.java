package bg.com.bo.bff.models.dtos;

@lombok.Data
public class TokenDataServiceResponse {
    private String accessToken;
    private String refreshToken;
}
