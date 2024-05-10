package bg.com.bo.bff.application.dtos.request.qr;

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
    @NotBlank(message = "start no puede estar en blanco")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "start debe estar en formato yyyy-MM-dd")
    @Schema(example = "2023-11-22", description = "Fecha Inicio")
    private String start;

    @NotBlank(message = "end no puede estar en blanco")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "end debe estar en formato yyyy-MM-dd")
    @Schema(example = "2024-04-30", description = "Fecha fin")
    private String end;
}
