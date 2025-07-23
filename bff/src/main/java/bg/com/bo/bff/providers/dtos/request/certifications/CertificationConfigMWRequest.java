package bg.com.bo.bff.providers.dtos.request.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationConfigMWRequest {

    private String personId;
    private String languageCode;
    private String requestCode;
    private String requestTypeCode;

}
