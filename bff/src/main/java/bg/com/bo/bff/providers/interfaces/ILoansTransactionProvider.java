package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.loans.mw.LoanPaymentMWRequest;
import bg.com.bo.bff.providers.dtos.response.loans.mw.*;

import java.io.IOException;

public interface ILoansTransactionProvider {
    LoanPaymentMWResponse payLoanInstallment(LoanPaymentMWRequest request) throws IOException;
}
