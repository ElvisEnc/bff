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

    @Schema(description = "Tiempo de expiración del token.")
    private Integer expiresIn;

    
    @Schema(description = "Tiempo de expiración del refresh token.")
    private Integer refreshExpiresIn;

    private UserDataResponse userData;

    @Schema(description = "Última conexión")
    private String lastConnectionDate;

    @Schema(description = "Cambio de clave")
    private Boolean keyChange;

    @Schema(description = "Mesaje del cambio de clave")
    private String keyChangeMessage;

    public enum StatusCode {
        SUCCESS,
        INVALID_DATA
    }
}
