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
public class SoftTokenDataEnrollmentResponse {

    @Schema(description = "Telefono del usuario")
    @JsonProperty("telephone")
    private String telephone;

    @Schema(description = "Email del usuario")
    @JsonProperty("email")
    private String email;
}
