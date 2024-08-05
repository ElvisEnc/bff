package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.commons.filters.interfaces.IFilter;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;

import java.math.BigDecimal;
import java.util.List;

public class AmountRangeFilter implements IFilter<AccountStatementsMWResponse.AccountStatementMW> {
    private final BigDecimal minAmount;
    private final BigDecimal maxAmount;

    public AmountRangeFilter(BigDecimal minAmount, BigDecimal maxAmount) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

    @Override
    public List<AccountStatementsMWResponse.AccountStatementMW> apply(List<AccountStatementsMWResponse.AccountStatementMW> list) {
        return list.stream()
                .filter(extract -> {
                    boolean isMinValid = minAmount == null || extract.getAmount().compareTo(minAmount) >= 0;
                    boolean isMaxValid = maxAmount == null || extract.getAmount().compareTo(maxAmount) <= 0;
                    return isMinValid && isMaxValid;
                })
                .toList();
    }
}
