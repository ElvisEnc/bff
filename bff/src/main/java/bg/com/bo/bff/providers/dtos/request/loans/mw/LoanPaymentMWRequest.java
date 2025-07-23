package bg.com.bo.bff.providers.dtos.request.loans.mw;

import bg.com.bo.bff.commons.annotations.generics.DescriptionChars;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
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
    private SupplementaryData supplementaryData;

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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupplementaryData {
        @DescriptionChars
        @Size(min = 25, max = 100, message = "La fuente de los fondos debe tener entre 25 y 100 caracteres.")
        @Schema(description = "Fuente de fondos para la transferencia", example = "Fuente de fondos para la transferencia")
        private String sourceOfFunds;
        private String destinationOfFunds;
    }
}
