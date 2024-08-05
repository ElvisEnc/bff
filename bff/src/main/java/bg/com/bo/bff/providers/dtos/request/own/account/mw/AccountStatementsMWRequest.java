package bg.com.bo.bff.providers.dtos.request.own.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatementsMWRequest {
    private String accountId;
    private String startDate;
    private String endDate;
    private String initCount;
    private String totalCount;
}
