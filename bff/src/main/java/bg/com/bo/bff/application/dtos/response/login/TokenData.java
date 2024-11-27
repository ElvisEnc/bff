package bg.com.bo.bff.application.dtos.response.login;

import lombok.Builder;

@lombok.Data
@Builder
public class TokenData {
    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private Integer refreshExpiresIn;
}
