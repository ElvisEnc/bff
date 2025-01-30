package bg.com.bo.bff.providers.dtos.response.remittance.mw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckRemittanceMWResponse {

    private String noRemittance;
    private String noConsult;
    private String amountReceived;
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
