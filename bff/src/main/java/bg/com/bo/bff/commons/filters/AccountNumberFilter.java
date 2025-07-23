package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.dtos.response.qr.QrGeneratedPaid;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.util.List;

public class AccountNumberFilter implements IFilter<QrGeneratedPaid> {
    private final List<Long> accountNumbers;

    public AccountNumberFilter(List<Long> accountNumbers) {
        this.accountNumbers = accountNumbers;
    }

    @Override
    public List<QrGeneratedPaid> apply(List<QrGeneratedPaid> list) {
        return list.stream()
                .filter(payment -> accountNumbers.contains(payment.getDestinationAccountNumber()))
                .toList();
    }
}
