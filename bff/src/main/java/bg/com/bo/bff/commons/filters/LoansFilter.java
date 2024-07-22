package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.dtos.request.loans.ListLoansRequest;
import bg.com.bo.bff.application.dtos.response.loans.ListLoansResponse;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class LoansFilter implements IFilter<ListLoansResponse> {
    private static final String LOAN_ID = "LOAN_ID";
    private final String field;
    private final boolean desc;

    public LoansFilter(ListLoansRequest.LoanFilters filters) {
        this.field = filters.getOrder() != null ? filters.getOrder().getField() : null;
        this.desc = filters.getOrder() != null && filters.getOrder().getDesc();
    }

    @Override
    public List<ListLoansResponse> apply(List<ListLoansResponse> list) {
        Comparator<ListLoansResponse> comparator;
        if (Objects.equals(field, LOAN_ID)) {
            comparator = Comparator.comparing(ListLoansResponse::getLoanId, Comparator.nullsLast(Comparator.naturalOrder()));
        } else {
            comparator = Comparator.comparing(ListLoansResponse::getExpirationDate, Comparator.nullsLast(Comparator.naturalOrder()));
        }
        list.sort(desc ? comparator.reversed() : comparator);
        return list;
    }
}

