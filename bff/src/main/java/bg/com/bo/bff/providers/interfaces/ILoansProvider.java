package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.loans.mw.*;

import java.io.IOException;

public interface ILoansProvider {

    ListLoansMWResponse getListLoansByPerson(String personId) throws IOException;

    LoanPaymentsMWResponse getListLoanPayments(String loanId, String loamNumber) throws IOException;

    LoanInsurancePaymentsMWResponse getListLoanInsurancePayments(String loanId, String loamNumber) throws IOException;

    LoanPlanMWResponse getLoanPlansPayments(String loanId, String personId) throws IOException;

    LoanDetailPaymentMWResponse getLoanDetailPayment(String loanId, String clientId) throws IOException;
}
