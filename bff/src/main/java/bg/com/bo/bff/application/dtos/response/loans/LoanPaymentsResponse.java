package bg.com.bo.bff.application.dtos.response.loans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanPaymentsResponse {
    private String date;
    private String accountEntry;
    private String advancedCapital;
    private String originalCapital;
    private String capitalPaid;
    private String expenses;
    private String interestAmountPaid;
    private String payLateFees;
    private String balance;
    private String typeMovement;
    private String totalInstallment;
    private String branch;
}
