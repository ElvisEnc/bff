package bg.com.bo.bff.application.dtos.request.notification.config;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterNotificationRequest {
    @NotNull()
    @Schema(description = "Tipo de envío para el registro de notificaciones ", example = "3")
    private Integer configurationId;

    @NotNull()
    @Schema(description = "Monto minimo para la notificación.", example = "10.50")
    private BigDecimal baseAmount;

    @Email
    @Schema(
            description = "Correo electrónico donde se enviará notificacion de credito a cuenta.",
            example = "nuevoganamovil@gmial.com"
    )
    private String email;
}
