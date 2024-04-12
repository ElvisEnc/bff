package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.ExtractDataResponse;
import bg.com.bo.bff.commons.enums.AccountStatementType;
import bg.com.bo.bff.commons.filters.AmountRangeFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.commons.filters.TypeFilter;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import bg.com.bo.bff.services.interfaces.IAccountStatementService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class AccountStatementService implements IAccountStatementService {
    private final IAccountStatementProvider iAccountStatementProvider;

    public AccountStatementService(IAccountStatementProvider iAccountStatementProvider) {
        this.iAccountStatementProvider = iAccountStatementProvider;
    }

    @Override
    public ExtractDataResponse getAccountStatement(ExtractRequest request, String accountId) throws IOException {
        String startDate = request.getFilters().getPagination().getStartDate();
        String endDate = request.getFilters().getPagination().getEndDate();
        String key = new StringBuilder().append(accountId).append("|").append(startDate).append("|").append(endDate).toString();

        String extractType = request.getFilters().getType();
        Double min = request.getFilters().getAmount().getMin();
        Double max = request.getFilters().getAmount().getMax();

        Boolean isPageOne = request.getFilters().getPagination().getPage() == 1;
        ClientToken clientToken = iAccountStatementProvider.generateToken();
        AccountReportBasicResponse basicResponse = iAccountStatementProvider.getAccountStatement(request, clientToken.getAccessToken(), accountId, key, isPageOne);

        List<AccountReportBasicResponse.AccountReportData> data = basicResponse.getData();

        data = new TypeFilter(AccountStatementType.getValueByCode(extractType)).apply(data);

        data = new AmountRangeFilter(min, max).apply(data);

        data = new PageFilter(request.getFilters().getPagination().getPage(), request.getFilters().getPagination().getPageSize()).apply(data);

        List<ExtractDataResponse.ExtractResponse> extractResponseList = data.stream().map(AccountStatementService::toProviderResponse).toList();

        ExtractDataResponse response = new ExtractDataResponse();
        response.setData(extractResponseList);
        return response;
    }

    public static ExtractDataResponse.ExtractResponse toProviderResponse(AccountReportBasicResponse.AccountReportData accountReportData) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("ACEP", 1);
        hashMap.put("ENPROC", 2);
        hashMap.put("RECH", 3);
        return ExtractDataResponse.ExtractResponse.builder()
                .status(String.valueOf(hashMap.get(accountReportData.getStatus())))
                .type(Objects.equals(accountReportData.getMoveType(), "D") ? AccountStatementType.DEBITO.getCode() : AccountStatementType.CREDITO.getCode())
                .amount(accountReportData.getAmount())
                .currency(accountReportData.getCurrencyCod())
                .channel(accountReportData.getBranchOffice())
                .dateMov(accountReportData.getProcessDate())
                .timeMov(accountReportData.getAccountingTime())
                .movBalance(accountReportData.getCurrentBalance())
                .seatNumber(String.valueOf(accountReportData.getSeatNumber()))
                .build();
    }
}
