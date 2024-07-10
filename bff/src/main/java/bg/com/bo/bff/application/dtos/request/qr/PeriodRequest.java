package bg.com.bo.bff.application.dtos.request.qr;

import bg.com.bo.bff.commons.annotations.DatePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodRequest {
    @Schema(example = "2023-11-22", description = "Fecha Inicio")
    @DatePattern
    private String start;

    @Schema(example = "2024-04-30", description = "Fecha fin")
    @DatePattern
    private String end;
}
