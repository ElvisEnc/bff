package bg.com.bo.bff.providers.dtos.response.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationSaveRequestMWResponse {

    private SaveResponse data;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveResponse{
        private String certPrice;
        private String requestDate;
        private String requestTime;
        private String fromCurrency;
        private String originAccount;
        private String clientAccountName;
        private String email;
        private String certDescription;
        private String dateRange;
        private String requestNumber;
    }


}
