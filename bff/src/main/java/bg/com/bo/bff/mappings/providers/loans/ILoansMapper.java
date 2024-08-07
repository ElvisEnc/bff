package bg.com.bo.bff.mappings.providers.loans;

import bg.com.bo.bff.application.dtos.response.loans.*;
import bg.com.bo.bff.providers.dtos.request.loans.mw.LoanPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;

import java.util.List;

public interface ILoansMapper {
    List<ListLoansResponse> convertResponse(ListLoansMWResponse mwResponse);

    List<LoanPaymentsResponse> convertResponse(LoanPaymentsMWResponse mwResponse);

    List<LoanInsurancePaymentsResponse> convertResponse(LoanInsurancePaymentsMWResponse mwResponse);

    List<LoanPlanResponse> convertResponse(LoanPlanMWResponse mwResponse);

    LoanDetailPaymentResponse convertResponse(LoanDetailPaymentMWResponse mwResponse);

    LoanPaymentMWRequest mapperRequest(String personId, String accountId, String correlativeId);

    LoanPaymentResponse convertResponse(LoanPaymentMWResponse mwResponse);
}
