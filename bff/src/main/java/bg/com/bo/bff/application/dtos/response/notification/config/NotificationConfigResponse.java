package bg.com.bo.bff.application.dtos.response.notification.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationConfigResponse {
    @JsonProperty("data")
    private List<Configuration> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Configuration {
        @Schema(description = "Tipo de envío.")
        @JsonProperty("identify")
        private Integer identify;

        @Schema(description = "Tipo de movimiento.")
        @JsonProperty("shippingType")
        private Byte shippingType;

        @Schema(description = "Monto minimo de la cual se enviará la notificación.")
        @JsonProperty("movementType")
        private String movementType;

        @Schema(description = "Fecha de expiración de la configuración.")
        @JsonProperty("statecode")
        private Byte statecode;

        @Schema(description = "Estado de la notificación.")
        @JsonProperty("identifyregistry")
        private String identifyregistry;

        @Schema(description = "ID de cuenta.")
        @JsonProperty("amount")
        private BigDecimal amount;

    }
}
