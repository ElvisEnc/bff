package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoanPaymentsMWResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoansMWResponse;

import java.io.IOException;
import java.util.Map;

public interface ILoansProvider {

    ListLoansMWResponse getListLoansByPerson(String personId, Map<String, String> parameters) throws IOException;

    ListLoanPaymentsMWResponse getListLoanPayments(String loanId, String personId, Map<String, String> parameters) throws IOException;
}
