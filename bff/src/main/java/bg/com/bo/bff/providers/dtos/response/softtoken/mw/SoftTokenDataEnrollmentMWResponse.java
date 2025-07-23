package bg.com.bo.bff.providers.dtos.response.softtoken.mw;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoftTokenDataEnrollmentMWResponse {
    @Schema(description = "Codigo de respuesta")
    private String codeError;

    @Schema(description = "Datos para el enrolamiento")
    private List<DataEnrollmentSoftToken> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DataEnrollmentSoftToken {
        @JsonProperty("telephone")
        private String telephone;
        @JsonProperty("email")
        private String email;
    }
}
