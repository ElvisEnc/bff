package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.commons.filters.interfaces.IFilter;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountReportBasicResponse;

import java.util.List;
import java.util.stream.Collectors;

public class AmountRangeFilter implements IFilter<AccountReportBasicResponse.AccountReportData> {
    private Double minAmount;
    private Double maxAmount;

    public AmountRangeFilter(Double minAmount, Double maxAmount) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    @Override
    public List<AccountReportBasicResponse.AccountReportData> apply(List<AccountReportBasicResponse.AccountReportData> list) {
        return list.stream()
                .filter(extract -> {
                    boolean isMinValid = minAmount == null || extract.getAmount() >= minAmount;
                    boolean isMaxValid = maxAmount == null || extract.getAmount() <= maxAmount;
                    return isMinValid && isMaxValid;
                })
                .collect(Collectors.toList());
    }
}
