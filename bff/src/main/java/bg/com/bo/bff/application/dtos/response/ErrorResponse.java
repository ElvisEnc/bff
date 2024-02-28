package bg.com.bo.bff.application.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.ToString;

@lombok.Data
@ToString
@AllArgsConstructor
public class ErrorResponse {
    @Schema(example = "UNAUTHORIZED", description = "Este es el código de error")
    private String code;

    @Schema(example = "UNAUTHORIZED", description = "Este es el mensaje de error")
    private String message;
}
