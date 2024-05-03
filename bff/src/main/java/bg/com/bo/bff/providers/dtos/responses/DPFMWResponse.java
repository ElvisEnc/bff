package bg.com.bo.bff.providers.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DPFMWResponse {
    private List<DPFManagerMWData> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DPFManagerMWData {
        private String accountDpf;
        private String numDpf;
        private String personNumber;
        private String numDpfBGA;
        private String ordinal;
        private String numCertificate;
        private String codeCurrency;
        private String highDate;
        private String term;
        private String expirationDate;
        private String rate;
        private String capital;
        private String interest;
        private String total;
        private String managementCod;
        private String payFormInterestCod;
        private String currencyDescription;
        private String currencyAbbreviation;
        private String statusCod;
        private String statusDescription;
        private String paymentMethod;
        private String drivingAccountDpf;
        private String productCod;
        private String productDescription;
        private String clientCod;
        private String clientName;
        private String plazaCod;
        private String plazaDescription;
    }
}