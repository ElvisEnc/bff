package bg.com.bo.bff.application.dtos.response.softtoken;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoftTokenWelcomeResponse {

    @Schema(description = "Mensaje de Bienvenida")
    @JsonProperty("message")
    private String message;
}
