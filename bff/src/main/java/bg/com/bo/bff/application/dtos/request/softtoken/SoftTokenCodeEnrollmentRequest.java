package bg.com.bo.bff.application.dtos.request.softtoken;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoftTokenCodeEnrollmentRequest {
    @Valid
    @Schema(description = "Email para enviar el codigo de seguridad")
    private String email;

    @Valid
    @Schema(description = "Telefono para enviar el codigo de seguridad")
    private String phone;
}
