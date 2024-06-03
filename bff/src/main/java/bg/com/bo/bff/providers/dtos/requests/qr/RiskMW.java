package bg.com.bo.bff.providers.dtos.requests.qr;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiskMW {
    private String paymentContextCode;
}
