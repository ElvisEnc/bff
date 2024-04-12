package bg.com.bo.bff.application.dtos.request.accountStatement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExtractFilter {

    @Valid
    private ExtractPagination pagination;

    @Schema(example = "true", description = "true = Ascendente, del más antiguo a hoy, false = Descendente, de hoy al más antiguo, por default")
    private Boolean isAsc;

    @Schema(example = "1", description = "1 Débito, 2 Crédito")
    private String type;

    private AmountRange amount;
}
