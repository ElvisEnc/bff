package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.ExtractDataResponse;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import bg.com.bo.bff.services.interfaces.IAccountStatementService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@Service
public class AccountStatementService implements IAccountStatementService {
    private final IAccountStatementProvider iAccountStatementProvider;

    public AccountStatementService(IAccountStatementProvider iAccountStatementProvider) {
        this.iAccountStatementProvider = iAccountStatementProvider;
    }

    @Override
    public ExtractDataResponse getAccountStatement(ExtractRequest request, String accountId) throws IOException {
        ClientToken clientToken = iAccountStatementProvider.generateToken();
        ExtractDataResponse basicResponse = iAccountStatementProvider.getAccountStatement(request, clientToken.getAccessToken(), accountId);
        return basicResponse;
    }

    public static ExtractDataResponse.ExtractResponse toProviderResponse(AccountReportBasicResponse.AccountReportData accountReportData) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("ACEP", 1);
        hashMap.put("ENPROC", 2);
        hashMap.put("RECH", 3);
        return ExtractDataResponse.ExtractResponse.builder()
                .status(String.valueOf(hashMap.get(accountReportData.getStatus())))
                .type(Objects.equals(accountReportData.getMoveType(), "D") ? "1" : "2")
                .amount(accountReportData.getAmount())
                .currency(accountReportData.getCurrencyCod())
                .channel(accountReportData.getBranchOffice())
                .dateMov(accountReportData.getProcessDate())
                .timeMov(accountReportData.getAccountingTime())
                .movBalance(accountReportData.getCurrentBalance())
                .build();
    }
}
