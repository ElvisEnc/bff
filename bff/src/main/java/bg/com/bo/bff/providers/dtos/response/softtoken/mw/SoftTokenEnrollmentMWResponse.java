package bg.com.bo.bff.providers.dtos.response.softtoken.mw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoftTokenEnrollmentMWResponse {
    @Schema(description = "Codigo de respuesta")
    private String codeError;

    @Schema(description = "Estado del enrolamiento")
    private String status;
}
