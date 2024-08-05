package bg.com.bo.bff.mappings.providers.statement;

import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class AccountStatementAdapter {
    public List<AccountStatementsMWResponse.AccountStatementMW> mapping(List<AccountStatementsMWResponse.AccountStatementMW> reportData) {
        Map<String, String> currencyMapping = new HashMap<>();
        currencyMapping.put("032", "ARS");
        currencyMapping.put("068", "BOB");
        currencyMapping.put("986", "BRL");
        currencyMapping.put("978", "EUR");
        currencyMapping.put("840", "USD");

        Map<String, String> statusMapping = new HashMap<>();
        statusMapping.put("ACEP", "Aprobada");
        statusMapping.put("ENPROC", "Pendiente");
        statusMapping.put("RECH", "Rechazada");

        for (AccountStatementsMWResponse.AccountStatementMW data : reportData) {
            String status = data.getStatus();
            String currency = data.getCurrencyCod();
            data.setStatus(statusMapping.get(status));
            data.setMoveType(Objects.equals(data.getMoveType(), "D") ? "Débito" : "Crédito");
            data.setCurrencyCod(currencyMapping.get(currency));
        }
        return reportData;
    }
}
