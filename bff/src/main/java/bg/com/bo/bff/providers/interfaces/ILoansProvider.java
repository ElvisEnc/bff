package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.loans.mw.Pcc01MWRequest;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;

import java.io.IOException;

public interface ILoansProvider {

    ListLoansMWResponse getListLoansByPerson(String personId, String clientId) throws IOException;

    LoanPaymentsMWResponse getListLoanPayments(String loanId, String loamNumber, String clientId) throws IOException;

    LoanInsurancePaymentsMWResponse getListLoanInsurancePayments(String loanId, String loamNumber, String clientId) throws IOException;

    LoanPlanMWResponse getLoanPlansPayments(String loanId, String clientId) throws IOException;

    LoanDetailPaymentMWResponse getLoanDetailPayment(String loanId, String clientId) throws IOException;

    Pcc01MWResponse validateControl(Pcc01MWRequest request) throws IOException;
}
