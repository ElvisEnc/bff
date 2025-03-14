package bg.com.bo.bff.application.dtos.response.certifications;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationPriceResponse {

    @Schema(description = "")
    private Integer roleId;

    @Schema(description = "")
    private Integer eventId;

    @Schema(description = "")
    private Integer amount;

    @Schema(description = "")
    private String currencyDes;

    @Schema(description = "")
    private Integer currencyCode;

    @Schema(description = "")
    private Integer rangeFrom;

    @Schema(description = "")
    private Integer rangeTo;

    @Schema(description = "")
    private String rangeType;

}
