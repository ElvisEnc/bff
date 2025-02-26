package bg.com.bo.bff.mappings.providers.account;

import bg.com.bo.bff.application.dtos.request.account.statement.AccountStatementsRequest;
import bg.com.bo.bff.application.dtos.request.account.statement.TransferMovementsRequest;
import bg.com.bo.bff.application.dtos.request.own.account.UpdateTransactionLimitRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.application.dtos.response.account.statement.TransferMovementsResponse;
import bg.com.bo.bff.application.dtos.response.destination.account.DestinationAccount;
import bg.com.bo.bff.application.dtos.response.own.account.OwnAccountsResponse;
import bg.com.bo.bff.application.dtos.response.own.account.TransactionLimitsResponse;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.ReportTransfersMWRequest;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.UpdateTransactionLimitMWRequest;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.OwnAccountsListMWResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.ReportTransfersMWResponse;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.TransactionLimitsMWResponse;

import java.util.List;

public interface IOwnAccountsMapper {
    List<OwnAccountsResponse> convertResponse(OwnAccountsListMWResponse mwResponse);

    UpdateTransactionLimitMWRequest mapperRequest(UpdateTransactionLimitRequest request);

    TransactionLimitsResponse convertResponse(TransactionLimitsMWResponse mwResponse);

    AccountStatementsMWRequest mapperRequest(String accountId, String personId, String init, String total, AccountStatementsRequest request);

    ReportTransfersMWRequest mapperRequest(String accountId, String personId, TransferMovementsRequest request);

    List<AccountStatementsResponse> convertResponse(AccountStatementsMWResponse mwResponse);

    List<TransferMovementsResponse> convertResponse(ReportTransfersMWResponse mwResponse);

    List<DestinationAccount> convertOwnAccountToDestinationAccount(OwnAccountsListMWResponse mwResponse, Integer type, String name);

}
