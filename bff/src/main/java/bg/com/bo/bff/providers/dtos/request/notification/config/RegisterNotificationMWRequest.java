package bg.com.bo.bff.providers.dtos.request.notification.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterNotificationMWRequest {
    private Integer identificator;
    private BigDecimal amountBase;
    private String email;
}
