package bg.com.bo.bff.mappings.providers.loans;

import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentRequest;
import bg.com.bo.bff.application.dtos.request.loans.Pcc01Request;
import bg.com.bo.bff.application.dtos.response.loans.*;
import bg.com.bo.bff.providers.dtos.request.loans.mw.LoanPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.request.loans.mw.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;

import java.util.List;

public interface ILoansMapper {
    List<ListLoansResponse> convertResponse(ListLoansMWResponse mwResponse);

    List<LoanPaymentsResponse> convertResponse(LoanPaymentsMWResponse mwResponse);

    List<LoanInsurancePaymentsResponse> convertResponse(LoanInsurancePaymentsMWResponse mwResponse);

    List<LoanPlanResponse> convertResponse(LoanPlanMWResponse mwResponse);

    LoanDetailPaymentResponse convertResponse(LoanDetailPaymentMWResponse mwResponse, String currencyCode);

    LoanPaymentMWRequest mapperRequest(String personId, String accountId, LoanPaymentRequest request);

    LoanPaymentResponse convertResponse(LoanPaymentMWResponse mwResponse);

    Pcc01MWRequest mapperRequest(String personId, String accountId, Pcc01Request request);
}
