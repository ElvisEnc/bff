package bg.com.bo.bff.application.dtos.request.qr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class QRPaymentRequest {
    private TargetAccount targetAccount;
    private Amount amount;
    private SupplementaryData supplementaryData;
    private Risk risk;
}
