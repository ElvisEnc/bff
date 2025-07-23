package bg.com.bo.bff.providers.dtos.request.own.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportTransfersMWRequest {

    private String accountId;
    private String startDate;
    private String endDate;
}
