package bg.com.bo.bff.providers.dtos.response.own.account.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportTransfersMWResponse {

    private List<TransferMW> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransferMW {

        private String transactionDate;
        private String createdTime;
        private String operation;
        private String toBankCode;
        private String accountId;
        private String accountEntry;
        private String toAccount;
        private String toHolderName;
        private String gloss;
        private BigDecimal amount;
        private String abbreviated;
        private String status;
        private String toBankName;
        private String clientId;
    }
}
