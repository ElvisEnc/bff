package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogoutRequest {
    @NotBlank
    @Schema(example = "adfsdfsdf", description = "Refresh token encodeado en base 64.")
    private String refreshToken;
}
