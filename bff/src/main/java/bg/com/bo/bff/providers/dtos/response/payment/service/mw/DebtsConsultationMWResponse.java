package bg.com.bo.bff.providers.dtos.response.payment.service.mw;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebtsConsultationMWResponse {
    private DebtsConsultationMW data;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DebtsConsultationMW {
        private String affiliationCode;
        private String serviceCode;
        private List<DebtsConsultationDetail> debtDetails;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DebtsConsultationDetail {
        private String invoiceCanModifyData;
        private String invoiceName;
        private String invoiceId;
        private String invoiceComplementId;
        private String invoiceEmail;
        private String description;
        private String referenceCode;
        private Integer monthPeriod;
        private Integer year;
        private BigDecimal commissionAmount;
        private String currencyCode;
        private BigDecimal amount;
        private BigDecimal accumulatedAmount;
        private Long identifier;
        private String validationType;
        private String detail;
        private String additionalDataDetails;
        private String paymentPlanCode;
        private String idGeneratedForDebt;
        private BigDecimal penaltyPayment;
        private String concept;
    }
}
