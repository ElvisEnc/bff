package bg.com.bo.bff.application.dtos.request.loyalty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterSubscriptionRequest {
    @NotBlank(message = "el email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    @Schema(description = "Email para realizar la suscripción", example = "123@dominio.org")
    private String email;
}
