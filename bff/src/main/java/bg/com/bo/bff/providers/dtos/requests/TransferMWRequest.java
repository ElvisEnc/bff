package bg.com.bo.bff.providers.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferMWRequest {
    private OwnAccount ownerAccount;
    private Account debtorAccount;
    private Account creditorAccount;
    private Amount instructedAmount;
    private SupplementaryData supplementaryData;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnAccount {
        private String schemeName;
        private String personId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Account {
        private String schemeName;
        private String identification;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Amount {
        private String currency;
        private BigDecimal amount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupplementaryData {
        private String description;
        private String sourceOfFunds;
        private String destinationOfFunds;
    }
}
