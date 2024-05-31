package bg.com.bo.bff.providers.dtos.requests.qr;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QRPaymentMWRequest {
    private OwnerAccount ownerAccount;
    private InstructedAmount instructedAmount;
    private DebtorAccount debtorAccount;
    private CreditorAccount creditorAccount;
    private SupplementaryMWData supplementaryData;
    private RiskMW risk;

}
