package bg.com.bo.bff.application.dtos.request.account.statement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtractFilter {
    @Valid
    private ExtractPagination pagination;

    @NotNull
    @Schema(example = "false", description = "true = Ascendente, del más antiguo a hoy, false = Descendente, de hoy al más antiguo, por default")
    private Boolean isAsc;

    @Schema(example = "1", description = "1 Débito, 2 Crédito")
    private String type;

    private AmountRange amount;
}
