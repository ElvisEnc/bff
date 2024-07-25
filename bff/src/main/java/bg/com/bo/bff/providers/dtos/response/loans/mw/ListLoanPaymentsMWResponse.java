package bg.com.bo.bff.providers.dtos.response.loans.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListLoanPaymentsMWResponse {
    private List<LoanPaymentMW> data;
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoanPaymentMW {
        private String date;
        private String accountEntry;
        private String advancedCapital;
        private String originalCapital;
        private String capitalPaid;
        private String expenses;
        private String interesAmountPaid;
        private String payLateFees;
        private String balance;
        private String typeMovement;
        private String totalInstallment;
        private String branch;
    }
}
