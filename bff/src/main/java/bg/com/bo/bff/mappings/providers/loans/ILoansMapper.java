package bg.com.bo.bff.mappings.providers.loans;

import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.providers.dtos.response.loans.mw.ListLoansMWResponse;

import java.util.List;

public interface ILoansMapper {
    List<ListLoansResponse> convertResponse(ListLoansMWResponse mwResponse);
}
