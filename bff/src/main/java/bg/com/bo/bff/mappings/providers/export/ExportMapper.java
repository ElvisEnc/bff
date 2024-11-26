package bg.com.bo.bff.mappings.providers.export;

import bg.com.bo.bff.application.dtos.request.export.AccountStatementExportRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.providers.dtos.request.own.account.mw.AccountStatementsMWRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class ExportMapper implements  IExportMapper{
    @Override
    public AccountStatementsMWRequest mapperRequest(String accountId, String init, String total, AccountStatementExportRequest request) {
        return AccountStatementsMWRequest.builder()
                .accountId(accountId)
                .startDate(request.getFilters().getDate().getStart())
                .endDate(request.getFilters().getDate().getEnd())
                .initCount(init)
                .totalCount(total)
                .build();
    }

    @Override
    public List<AccountStatementsResponse> convertResponse(List<AccountStatementsResponse> reportData) {
        Map<String, String> currencyMapping = new HashMap<>();
        currencyMapping.put("032", "ARS");
        currencyMapping.put("068", "BOB");
        currencyMapping.put("986", "BRL");
        currencyMapping.put("978", "EUR");
        currencyMapping.put("840", "USD");

        Map<String, String> statusMapping = new HashMap<>();
        statusMapping.put("1", "Aprobada");
        statusMapping.put("2", "Pendiente");
        statusMapping.put("3", "Rechazada");

        for (AccountStatementsResponse data : reportData) {
            String status = data.getStatus();
            String currency = data.getCurrencyCode();
            data.setStatus(statusMapping.get(status));
            data.setMovementType(Objects.equals(data.getMovementType(), "D") ? "Débito" : "Crédito");
            data.setCurrencyCode(currencyMapping.get(currency));
        }
        return reportData;
    }
}
