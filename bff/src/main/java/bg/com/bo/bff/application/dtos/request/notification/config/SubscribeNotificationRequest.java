package bg.com.bo.bff.application.dtos.request.notification.config;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeNotificationRequest {
    @NotBlank()
    @Schema(description = "Es el Token para el envio de Notificaciones Push.", example = "disahdakjsdi3u4298237423h")
    private String devicePushToken;

}
