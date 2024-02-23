package bg.com.bo.bff.model.dtos;

@lombok.Data
public class TokenDataServiceResponse {
    private String accessToken;
    private String refreshToken;
}
