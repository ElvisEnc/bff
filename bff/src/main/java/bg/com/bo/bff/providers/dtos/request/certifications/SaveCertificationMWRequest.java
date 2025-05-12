package bg.com.bo.bff.providers.dtos.request.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveCertificationMWRequest {

    private String personId;
    private String appCode;
    private String accountId;
    private String chargeFeeId;
    private String typeCode;
    private String requestCode;
    private String nit;
    private String clientName;
    private String initDate;
    private String endDate;

}
