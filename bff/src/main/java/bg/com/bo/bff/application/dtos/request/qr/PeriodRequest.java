package bg.com.bo.bff.application.dtos.request.qr;

import bg.com.bo.bff.commons.annotations.DatePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodRequest {
    @NotBlank
    @DatePattern
    @Schema(example = "2023-11-22", description = "Fecha Inicio")
    private String start;

    @NotBlank
    @DatePattern
    @Schema(example = "2024-04-30", description = "Fecha fin")
    private String end;
}
