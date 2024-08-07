package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.loans.mw.LoanPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;

import java.io.IOException;
import java.util.Map;

public interface ILoansTransactionProvider {

    LoanPaymentMWResponse payLoanInstallment (LoanPaymentMWRequest request, Map<String, String> parameters) throws IOException;
}
