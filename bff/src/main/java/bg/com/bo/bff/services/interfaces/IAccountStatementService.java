package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.request.account.statement.RegenerateVoucherRequest;
import bg.com.bo.bff.application.dtos.request.account.statement.TransferMovementsRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.application.dtos.response.account.statement.RegenerateVoucherResponse;
import bg.com.bo.bff.application.dtos.response.account.statement.TransferMovementsResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IAccountStatementService {

    List<AccountStatementsResponse> getAccountStatement(String accountId, String personId, AccountStatementsRequest request, Map<String, String> parameter) throws IOException;

    List<TransferMovementsResponse> getTransferMovements(String accountId, String personId, TransferMovementsRequest request, Map<String, String> parameter) throws IOException;

    RegenerateVoucherResponse getVoucher(RegenerateVoucherRequest request, Map<String, String> parameter) throws IOException;
}
