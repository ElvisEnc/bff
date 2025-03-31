package bg.com.bo.bff.providers.dtos.request.remittance.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositRemittanceWUMWRequest {

    private String personId;
    private String applicationId;
    private String noRemittance;
    private String noConsult;
    private String jtsOidAccount;
    private String pCType;
    private String originFund;
    private String originDestination;
}
