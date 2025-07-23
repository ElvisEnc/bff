package bg.com.bo.bff.application.dtos.response.login;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEnrollmentResponse {
    @Schema(example = "1234567", description = "Es el código de persona")
    private String personId;

    @Schema(example = "1", description = "1 = Enrolado. 2 = No Enrolado")
    private Integer statusCode;
}
