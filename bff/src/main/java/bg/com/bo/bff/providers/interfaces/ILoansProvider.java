package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.loans.mw.*;

import java.io.IOException;
import java.util.Map;

public interface ILoansProvider {

    ListLoansMWResponse getListLoansByPerson(String personId, Map<String, String> parameters) throws IOException;

    LoanPaymentsMWResponse getListLoanPayments(String loanId, String loamNumber, Map<String, String> parameters) throws IOException;

    LoanInsurancePaymentsMWResponse getListLoanInsurancePayments(String loanId, String loamNumber, Map<String, String> parameters) throws IOException;

    LoanPlanMWResponse getLoanPlansPayments(String loanId, String personId, Map<String, String> parameters) throws IOException;

    LoanDetailPaymentMWResponse getLoanDetailPayment(String loanId, String personId, Map<String, String> parameters) throws IOException;
}
