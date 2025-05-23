package bg.com.bo.bff.application.dtos.response.notification.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    @Schema(example = "454525", description = "Valor que el identificador de la suscripcion de notificación.")
    private Integer identifier;
}
