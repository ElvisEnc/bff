package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.response.loans.LoanPaymentsResponse;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LoanPaymentsDateFilter implements IFilter<LoanPaymentsResponse> {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LoanPaymentsDateFilter(LoanPaymentsRequest request) {
        this.startDate = LocalDate.parse(request.getFilters().getPaymentDate().getStart(), DATE_FORMATTER);
        this.endDate = LocalDate.parse(request.getFilters().getPaymentDate().getEnd(), DATE_FORMATTER);
    }

    @Override
    public List<LoanPaymentsResponse> apply(List<LoanPaymentsResponse> list) {
        return list.stream()
                .filter(response -> {
                    LocalDate date = LocalDate.parse(response.getDate(), DATE_FORMATTER);
                    return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate));
                })
                .toList();
    }
}
