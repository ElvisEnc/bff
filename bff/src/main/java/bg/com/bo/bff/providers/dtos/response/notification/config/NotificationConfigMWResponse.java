package bg.com.bo.bff.providers.dtos.response.notification.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationConfigMWResponse {
    @JsonProperty("data")
    private List<NotificationConfig> data;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NotificationConfig {
        @JsonProperty("identify")
        private Integer identify;

        @JsonProperty("shippingType")
        private Byte shippingType;

        @JsonProperty("movementType")
        private String movementType;

        @JsonProperty("statecode")
        private Byte statecode;

        @JsonProperty("identifyregistry")
        private String identifyregistry;

        @JsonProperty("amount")
        private BigDecimal amount;
    }
}
