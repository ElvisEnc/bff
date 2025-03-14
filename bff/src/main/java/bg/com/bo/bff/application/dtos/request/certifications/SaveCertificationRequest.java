package bg.com.bo.bff.application.dtos.request.certifications;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveCertificationRequest {

    @Schema(description = "")
    private String personId;

    @Schema(description = "")
    private String accountId;

    @Schema(description = "")
    private String chargeFeeId;

    @Schema(description = "")
    private String typeCode;

    @Schema(description = "")
    private String requestCode;

    @Schema(description = "")
    private String nit;

    @Schema(description = "")
    private String clientName;

    @Schema(description = "")
    private String initDate;

    @Schema(description = "")
    private String endDate;

}
