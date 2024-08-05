package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.request.own.account.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.application.dtos.response.own.account.OwnAccountsResponse;
import bg.com.bo.bff.application.dtos.response.own.account.TransactionLimitsResponse;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.UpdateTransactionLimitMWRequest;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.OwnAccountsListMWResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.TransactionLimitsMWResponse;

import java.util.List;

public interface IOwnAccountsMapper {
    List<OwnAccountsResponse> convertResponse(OwnAccountsListMWResponse mwResponse);

    UpdateTransactionLimitMWRequest mapperRequest(UpdateTransactionLimitRequest request);

    TransactionLimitsResponse convertResponse(TransactionLimitsMWResponse mwResponse);

    AccountStatementsMWRequest mapperRequest(String accountId, String init, String total, AccountStatementsRequest request);

    List<AccountStatementsResponse> convertResponse(AccountStatementsMWResponse mwResponse);
}
