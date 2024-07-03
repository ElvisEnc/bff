package bg.com.bo.bff.providers.dtos.request.own.account.mw;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountReportBasicRequest {
    private String accountId;
    private String startDate;
    private String endDate;
    private String initCount;
    private String totalCount;
}
