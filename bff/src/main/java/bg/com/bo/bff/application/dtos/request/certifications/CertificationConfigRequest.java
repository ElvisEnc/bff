package bg.com.bo.bff.application.dtos.request.certifications;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationConfigRequest {

    @JsonProperty
    @OnlyNumber
    private String personId;

    @JsonProperty
    @OnlyNumber
    private String languageCode;

    @JsonProperty
    @OnlyNumber
    private String requestCode;

    @JsonProperty
    @OnlyNumber
    private String requestTypeCode;

}
