package bg.com.bo.bff.application.dtos.response.softtoken;

import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.providers.models.enums.middleware.response.IGenericControllerResponse;
import bg.com.bo.bff.providers.models.enums.middleware.softtoken.SoftTokenMiddlewareResponse;
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
public class SoftTokenValidationEnrollmentResponse {
    @Schema(description = "Estado de la respuesta de enrolamiento")
    @JsonProperty("status")
    private String status;

    public static SoftTokenValidationEnrollmentResponse instance(SoftTokenMiddlewareResponse softTokenMiddlewareResponse) {
        return SoftTokenValidationEnrollmentResponse.builder()
                .status(softTokenMiddlewareResponse.getMessage())
                .build();
    }
}
