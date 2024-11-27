package bg.com.bo.bff.application.dtos.response.login;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@lombok.Data
@Builder
public class TokenDataResponse {
    @Schema(description = "Access Token generado.")
    private String accessToken;
    @Schema(description = "Refresh Token generado.")
    private String refreshToken;
    @Schema(description = "Tiempo de expiración del token en segundos.")
    private Integer expiresIn;
    @Schema(description = "Tiempo de expiración del refresh token en segundos.")
    private Integer refreshExpiresIn;
}
