package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface ILoansService {

    List<ListLoansResponse> getListLoansByPerson(String personId, ListLoansRequest request, Map<String, String> parameter) throws IOException;
}
