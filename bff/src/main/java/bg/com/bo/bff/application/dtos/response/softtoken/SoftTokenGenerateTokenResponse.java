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
public class SoftTokenGenerateTokenResponse {

    @Schema(description = "Token")
    @JsonProperty("token")
    private String token;

    @Schema(description = "Duracion del token")
    @JsonProperty("tokenDuration")
    private Integer tokenDuration;
}
