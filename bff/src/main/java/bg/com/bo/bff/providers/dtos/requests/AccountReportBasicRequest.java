package bg.com.bo.bff.providers.dtos.requests;

import lombok.Builder;

@lombok.Data
@Builder
public class AccountReportBasicRequest {
    private String accountId;
    private String startDate;
    private String endDate;
    private String initCount;
    private String totalCount;
}
