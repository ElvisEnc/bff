package bg.com.bo.bff.mappings.providers.loans;

import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanInsurancePaymentsResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanPaymentsResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanPlanResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.LoanInsurancePaymentsMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.LoanPaymentsMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoansMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.LoanPlanMWResponse;

import java.util.List;

public interface ILoansMapper {
    List<ListLoansResponse> convertResponse(ListLoansMWResponse mwResponse);

    List<LoanPaymentsResponse> convertResponse(LoanPaymentsMWResponse mwResponse);

    List<LoanInsurancePaymentsResponse> convertResponse(LoanInsurancePaymentsMWResponse mwResponse);

    List<LoanPlanResponse> convertResponse(LoanPlanMWResponse mwResponse);
}
