package bg.com.bo.bff.application.dtos.request.commons;

import bg.com.bo.bff.commons.annotations.DatePattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeriodRequest {
    @DatePattern
    @Schema(example = "2023-11-22", description = "Fecha Inicio")
    private String start;

    @DatePattern
    @Schema(example = "2024-04-30", description = "Fecha fin")
    private String end;
}
