package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.dtos.request.loans.LoanPaymentsRequest;
import bg.com.bo.bff.application.dtos.response.loans.LoanPaymentsResponse;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LoanPaymentsOrderFilter implements IFilter<LoanPaymentsResponse> {
    private final String field;
    private final boolean desc;
    private static final String DATE = "DATE";
    private static final String INTEREST_PAID = "INTEREST_PAID";
    private static final String CAPITAL_PAID = "CAPITAL_PAID";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public LoanPaymentsOrderFilter(LoanPaymentsRequest request) {
        this.field = request.getFilters().getOrder() != null ? request.getFilters().getOrder().getField() : DATE;
        this.desc = request.getFilters().getOrder() == null || request.getFilters().getOrder().getDesc();
    }

    @Override
    public List<LoanPaymentsResponse> apply(List<LoanPaymentsResponse> list) {
        List<LoanPaymentsResponse> modifiableList = new ArrayList<>(list);
        Comparator<LoanPaymentsResponse> comparator = getComparatorForField(field);
        modifiableList.sort(desc ? comparator.reversed() : comparator);
        return modifiableList;
    }

    private Comparator<LoanPaymentsResponse> getComparatorForField(String field) {
        return switch (field) {
            case INTEREST_PAID ->
                    Comparator.comparing(response -> new BigDecimal(response.getInterestAmountPaid()), Comparator.nullsLast(Comparator.naturalOrder()));
            case CAPITAL_PAID ->
                    Comparator.comparing(response -> new BigDecimal(response.getCapitalPaid()), Comparator.nullsLast(Comparator.naturalOrder()));
            default ->
                    Comparator.comparing(response -> LocalDate.parse(response.getDate(), DATE_FORMATTER), Comparator.nullsLast(Comparator.naturalOrder()));
        };
    }
}
