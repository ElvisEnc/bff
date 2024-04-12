package bg.com.bo.bff.commons.filters;

import bg.com.bo.bff.commons.filters.interfaces.IFilter;
import bg.com.bo.bff.providers.dtos.responses.AccountReportBasicResponse;

import java.util.ArrayList;
import java.util.List;

public class PageFilter implements IFilter<AccountReportBasicResponse.AccountReportData> {

    private int page;
    private int pageSize;

    public PageFilter(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public List<AccountReportBasicResponse.AccountReportData> apply(List<AccountReportBasicResponse.AccountReportData> list) {
        int start = (page - 1) * pageSize;
        int end = start + pageSize - 1;

        int totalRecords = list.size();
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        if (page > totalPages) {
            return new ArrayList<>();
        }

        if (end >= totalRecords) {
            end = totalRecords - 1;
        }
        return list.subList(start, end + 1);
    }
}
