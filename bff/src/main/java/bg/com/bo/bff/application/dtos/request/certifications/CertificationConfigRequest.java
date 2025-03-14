package bg.com.bo.bff.application.dtos.request.certifications;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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
