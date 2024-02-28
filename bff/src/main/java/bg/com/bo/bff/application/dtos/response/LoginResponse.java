package bg.com.bo.bff.application.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    @Schema(description = "Access Token generado.")
    private String accessToken;
    @Schema(description = "Refresh Token generado.")
    private String refreshToken;
    @Schema(example = "1", description = "Access Token generado.")
    private String personId;

    public enum StatusCode {
        SUCCESS,
        INVALID_DATA
    }
}
