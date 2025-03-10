package bg.com.bo.bff.providers.dtos.request.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateTypeMWRequest {

    private Integer requestCode;
    private Integer typeCode;
    private String description;

}
