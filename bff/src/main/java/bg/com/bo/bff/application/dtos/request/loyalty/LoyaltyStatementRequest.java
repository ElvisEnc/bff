package bg.com.bo.bff.application.dtos.request.loyalty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyaltyStatementRequest {

    @Valid
    @Schema(description = "Fecha inicial ")
    private String startDate;

    @Valid
    @Schema(description = "Fecha final")
    private String endDate;
}
