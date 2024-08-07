package bg.com.bo.bff.providers.dtos.request.loans.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanPaymentMWRequest {
    private OwnAccount ownerAccountRequest;
    private DebtorAccount debtorAccountRequest;
    private CreditorAccount creditorAccountRequest;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnAccount {
        private String schemaName;
        private String personId;
        private String companyId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DebtorAccount {
        private String schemaName;
        private String identification;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreditorAccount {
        private String schemaName;
        private String sessionId;
    }
}
