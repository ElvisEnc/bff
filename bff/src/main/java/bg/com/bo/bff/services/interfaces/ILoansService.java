package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanInsurancePaymentsResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanPaymentsResponse;
import bg.com.bo.bff.application.dtos.response.loans.LoanPlanResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface ILoansService {

    List<ListLoansResponse> getListLoansByPerson(String personId, ListLoansRequest request, Map<String, String> parameter) throws IOException;

    List<LoanPaymentsResponse> getLoanPayments(String loanId, String personId, LoanPaymentsRequest request, Map<String, String> parameter) throws IOException;

    List<LoanInsurancePaymentsResponse> getLoanInsurancePayments(String loanId, String personId, LoanPaymentsRequest request, Map<String, String> parameter) throws IOException;

    List<LoanPlanResponse> getLoanPlans(String loanId, String personId, Map<String, String> parameter) throws IOException;
}
