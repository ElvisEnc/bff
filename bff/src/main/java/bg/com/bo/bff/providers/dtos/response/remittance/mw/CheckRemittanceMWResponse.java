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
public class CheckRemittanceMWResponse {

    private List<CheckRemittanceMWResponse.CheckRemittanceMW> data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CheckRemittanceMW {

        private String noRemittance;
        private int noConsult;
        private BigDecimal amountReceived;
        private String currencyReceived;
        private String countryEmission;
        private String plazaOrigin;
        private String payer;
        private String noTelephone;
        private String telephoneBeneficiary;
        private String countryDestination;
        private String plazaDestination;
        private String beneficiary;
        private String noDocument;
        private String documentType;
        private String extension;
    }
}
