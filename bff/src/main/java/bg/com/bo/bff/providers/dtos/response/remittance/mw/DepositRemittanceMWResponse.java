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

        private String codRemittance;
        private int accountingEntry;
        private String startTime;
        private String description;
        private String nameRemittance;
        private BigDecimal amountReceived;
        private String currencyReceived;
        private BigDecimal exchangeRate;
        private BigDecimal commission;
        private BigDecimal creditedAmount;
    }
}
