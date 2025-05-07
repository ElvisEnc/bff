package bg.com.bo.bff.application.dtos.response.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveCertificationResponse {

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
