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
public class LoanPlanMWResponse {
    private List<LoanPlanMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoanPlanMW {
        private Long identifier;
        private Long loanId;
        private Long loanNumber;
        private Long quotaNumber;
        private String dateInit;
        private String dateDue;
        private String quotaType;
        private BigDecimal capital;
        private BigDecimal interest;
        private BigDecimal charge1;
        private BigDecimal charge2;
        private BigDecimal charge3;
        private BigDecimal charge4;
        private BigDecimal charge5;
        private BigDecimal charge6;
        private BigDecimal remanent;
        private String dateRegister;
        private Long clientCode;
        private String clientName;
        private String product;
        private String branchName;
        private String currency;
        private String disbursementDate;
        private BigDecimal nominalRate;
        private Long period;
        private BigDecimal teac;
        private BigDecimal disbursementAmount;
        private Long timeLimit;
        private String rateType;
        private String nameTypeRate;
        private String dateReviewRate;
        private BigDecimal baseRateReviewPoint;
        private BigDecimal baseRate;
        private String paymentTypeInterest;
        private Long quantityDues;
    }
}
