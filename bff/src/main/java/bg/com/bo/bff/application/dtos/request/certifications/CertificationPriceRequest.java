package bg.com.bo.bff.application.dtos.request.certifications;

import bg.com.bo.bff.commons.annotations.DatePattern;
import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.commons.annotations.ValidText;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationPriceRequest {

    @JsonProperty
    @OnlyNumber
    private String personId;

    @JsonProperty
    @DatePattern
    private String initDate;

    @JsonProperty
    @DatePattern
    private String endDate;

    @JsonProperty
    @ValidText
    private String certCode;

    @JsonProperty
    @OnlyNumber
    private String certTypeCode;

}
