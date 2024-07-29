package bg.com.bo.bff.providers.dtos.response.loans.mw;

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
public class LoanInsurancePaymentsMWResponse {
    private List<LoanInsurancePaymentMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoanInsurancePaymentMW {
        private Integer paymentNumber;
        private String paymentDate;
        private Long warrantyNumber;
        private String description;
        private String currency;
        private String currencyDescription;
        private BigDecimal amount;
        private Integer record;
    }
}

