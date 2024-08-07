package bg.com.bo.bff.mappings.providers.export;

import bg.com.bo.bff.application.dtos.request.account.statement.ExportRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;

import java.util.List;

public interface IExportMapper {
    AccountStatementsMWRequest mapperRequest(String accountId, String init, String total, ExportRequest request);

    List<AccountStatementsResponse> mapping(List<AccountStatementsResponse> reportData);
}
