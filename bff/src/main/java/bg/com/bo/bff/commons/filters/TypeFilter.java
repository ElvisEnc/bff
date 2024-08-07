package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.application.dtos.response.account.statement.AccountStatementsResponse;
import bg.com.bo.bff.commons.filters.interfaces.IFilter;

import java.util.List;

public class TypeFilter implements IFilter<AccountStatementsResponse> {

    private final String movementType;

    public TypeFilter(String movementType) {
        this.movementType = movementType;
    }

    @Override
    public List<AccountStatementsResponse> apply(List<AccountStatementsResponse> list) {
        return list.stream()
                .filter(extract -> movementType == null || movementType.equals(extract.getMovementType()))
                .toList();
    }
}
