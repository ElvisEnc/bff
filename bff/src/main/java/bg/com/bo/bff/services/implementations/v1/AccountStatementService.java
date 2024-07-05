package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.account.statement.AmountRange;
import bg.com.bo.bff.application.dtos.request.account.statement.ExtractFilter;
import bg.com.bo.bff.application.dtos.request.account.statement.ExtractRequest;
import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementExtractResponse;
import bg.com.bo.bff.commons.enums.AccountStatementType;
import bg.com.bo.bff.commons.filters.AmountRangeFilter;
import bg.com.bo.bff.commons.filters.PageFilter;
import bg.com.bo.bff.commons.filters.TypeFilter;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountReportBasicResponse;
import bg.com.bo.bff.providers.interfaces.IAccountStatementProvider;
import bg.com.bo.bff.services.interfaces.IAccountStatementService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class AccountStatementService implements IAccountStatementService {
    private final IAccountStatementProvider iAccountStatementProvider;

    public AccountStatementService(IAccountStatementProvider iAccountStatementProvider) {
        this.iAccountStatementProvider = iAccountStatementProvider;
    }

    @Override
    public AccountStatementExtractResponse getAccountStatement(ExtractRequest request, String accountId) throws IOException {
        String startDate = request.getFilters().getPagination().getStartDate();
        String endDate = request.getFilters().getPagination().getEndDate();
        String key = new StringBuilder().append(accountId).append("|").append(startDate).append("|").append(endDate).toString();

        String extractType = request.getFilters().getType();
        Double min = Optional.ofNullable(request.getFilters())
                .map(ExtractFilter::getAmount)
                .map(AmountRange::getMin)
                .orElse(null);
        Double max = Optional.ofNullable(request.getFilters())
                .map(ExtractFilter::getAmount)
                .map(AmountRange::getMax)
                .orElse(null);

        Boolean isPageOne = request.getFilters().getPagination().getPage() == 1;
        ClientToken clientToken = iAccountStatementProvider.generateToken();
        AccountReportBasicResponse basicResponse = iAccountStatementProvider.getAccountStatement(request, clientToken.getAccessToken(), accountId, key, isPageOne);

        List<AccountReportBasicResponse.AccountReportData> data = basicResponse.getData();

        if (Boolean.FALSE.equals(request.getFilters().getIsAsc())) {
            Collections.reverse(data);
        }

        data = new TypeFilter(AccountStatementType.getValueByCode(extractType)).apply(data);

        data = new AmountRangeFilter(min, max).apply(data);

        data = new PageFilter(request.getFilters().getPagination().getPage(), request.getFilters().getPagination().getPageSize()).apply(data);

        List<AccountStatementExtractResponse.AccountStatementExtract> extractResponseList = data.stream().map(AccountStatementService::toProviderResponse).toList();

        AccountStatementExtractResponse response = new AccountStatementExtractResponse();
        response.setData(extractResponseList);
        return response;
    }

    public static AccountStatementExtractResponse.AccountStatementExtract toProviderResponse(AccountReportBasicResponse.AccountReportData accountReportData) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("ACEP", 1);
        hashMap.put("ENPROC", 2);
        hashMap.put("RECH", 3);

        return AccountStatementExtractResponse.AccountStatementExtract.builder()
                .status(String.valueOf(hashMap.get(accountReportData.getStatus())))
                .type(Objects.equals(accountReportData.getMoveType(), "D") ? AccountStatementType.DEBITO.getCode() : AccountStatementType.CREDITO.getCode())
                .amount(accountReportData.getAmount())
                .currency(accountReportData.getCurrencyCod())
                .channel(accountReportData.getBranchOffice())
                .dateMov(Util.formatDate(accountReportData.getProcessDate()))
                .timeMov(accountReportData.getAccountingTime())
                .movBalance(accountReportData.getCurrentBalance())
                .seatNumber(String.valueOf(accountReportData.getSeatNumber()))
                .description(accountReportData.getDescription())
                .build();
    }
}
