package bg.com.bo.bff.application.dtos.request.account.statement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportFilter {
    @NotBlank
    @Schema(example = "2023-11-22", description = "Fecha Inicio")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha de inicio debe estar en formato 'AAAA-MM-DD'")
    private String startDate;

    @NotBlank
    @Schema(example = "2023-12-31", description = "Fecha fin")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha de fin debe estar en formato 'AAAA-MM-DD'")
    private String endDate;

    @Schema(example = "false", description = "true = Ascendente, del más antiguo a hoy, false = Descendente, de hoy al más antiguo, por default")
    private Boolean isAsc;

    @Schema(example = "1", description = "1 Débito, 2 Crédito")
    private String type;

    private AmountRange amount;
}
