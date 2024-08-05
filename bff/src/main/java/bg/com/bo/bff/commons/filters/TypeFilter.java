package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.commons.filters.interfaces.IFilter;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountStatementsMWResponse;

import java.util.List;

public class TypeFilter implements IFilter<AccountStatementsMWResponse.AccountStatementMW> {

    private final String tipeMovement;

    public TypeFilter(String tipeMovement) {
        this.tipeMovement = tipeMovement;
    }

    @Override
    public List<AccountStatementsMWResponse.AccountStatementMW> apply(List<AccountStatementsMWResponse.AccountStatementMW> list) {
        return list.stream()
                .filter(extract -> tipeMovement == null || tipeMovement.equals(extract.getMoveType()))
                .toList();
    }
}
