package bg.com.bo.bff.providers.dtos.response.remittance.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositRemittanceMWResponse {

    private List<DepositRemittanceMWResponse.DepositRemittanceMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DepositRemittanceMW {

        private int remittanceCode;
        private int accountingEntry;
        private String startTime;
        private String description;
        private String remittanceName;
        private BigDecimal receivedAmount;
        private String receivedCurrency;
        private BigDecimal exchangeRate;
        private BigDecimal commission;
        private BigDecimal creditedAmount;
    }
}
