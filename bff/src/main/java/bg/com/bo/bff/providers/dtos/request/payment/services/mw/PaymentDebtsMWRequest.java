package bg.com.bo.bff.providers.dtos.request.payment.services.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDebtsMWRequest {

    private PaymentOwnerAccount ownerAccount;
    private PaymentAmount instructedAmount;
    private PaymentDebtor debtorAccount;
    private PaymentSupplementary supplementaryData;
    private PaymentRisk risk;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentOwnerAccount {
        private String schemeName;
        private String personId;
        private String companyId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentAmount {
        private String currency;
        private BigDecimal amount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentDebtor {
        private String schemeName;
        private String identification;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentSupplementary {
        private String idGeneratedForDebt;
        private String invoiceNITCI;
        private String invoiceName;
        private String invoiceType;
        private String invoiceComplementId;
        private String invoiceEmail;
        private String company;
        private String affiliationCode;
        private String serviceCode;
        private String description;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentRisk {
        private String paymentContextCode;
    }
}
