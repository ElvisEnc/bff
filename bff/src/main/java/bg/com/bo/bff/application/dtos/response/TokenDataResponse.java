package bg.com.bo.bff.application.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

@lombok.Data
public class TokenDataResponse {
    @Schema(description = "Access Token generado.")
    private String accessToken;
    @Schema(description = "Refresh Token generado.")
    private String refreshToken;
}
