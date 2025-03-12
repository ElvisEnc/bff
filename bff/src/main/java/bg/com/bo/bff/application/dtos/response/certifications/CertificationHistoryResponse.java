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
public class CertificationHistoryResponse {

    @Schema(description = "")
    private String requestNumber;

    @Schema(description = "")
    private String day;

    @Schema(description = "")
    private String month;

    @Schema(description = "")
    private String year;

    @Schema(description = "")
    private String title;

    @Schema(description = "")
    private String docNumber;

    @Schema(description = "")
    private String state;

    @Schema(description = "")
    private String mail;

}
