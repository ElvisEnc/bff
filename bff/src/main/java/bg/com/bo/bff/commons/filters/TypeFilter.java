package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.commons.filters.interfaces.IFilter;
import bg.com.bo.bff.providers.dtos.response.own.account.mw.AccountReportBasicResponse;

import java.util.List;
import java.util.stream.Collectors;

public class TypeFilter implements IFilter<AccountReportBasicResponse.AccountReportData> {

    private String tipeMovement;

    public TypeFilter(String tipeMovement) {
        this.tipeMovement = tipeMovement;
    }

    @Override
    public List<AccountReportBasicResponse.AccountReportData> apply(List<AccountReportBasicResponse.AccountReportData> list) {
        return list.stream()
                .filter(extract -> tipeMovement == null || tipeMovement.equals(extract.getMoveType()))
                .collect(Collectors.toList());
    }
}
