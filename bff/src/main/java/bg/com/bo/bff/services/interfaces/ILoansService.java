package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.response.loans.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ILoansService {

    List<ListLoansResponse> getListLoansByPerson(String personId, ListLoansRequest request) throws IOException;

    List<LoanPaymentsResponse> getLoanPayments(String loanId, String personId, LoanPaymentsRequest request) throws IOException;

    List<LoanInsurancePaymentsResponse> getLoanInsurancePayments(String loanId, String personId, LoanPaymentsRequest request) throws IOException;

    List<LoanPlanResponse> getLoanPlans(String loanId, String personId) throws IOException;

    LoanDetailPaymentResponse getLoanDetailPayment(String loanId, String personId, String clientId) throws IOException;

    LoanPaymentResponse payLoanInstallment(String personId, String accountId, String correlativeId) throws IOException;
}
