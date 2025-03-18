package bg.com.bo.bff.providers.dtos.request.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationPriceMWRequest {

    private String personId;
    private String appCode;
    private String session;
    private String initDate;
    private String endDate;
    private String certCode;
    private String certTypeCode;

}
