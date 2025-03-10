package bg.com.bo.bff.application.dtos.response.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationTypesResponse {

    private Integer requestCode;
    private Integer typeCode;
    private String description;

}
