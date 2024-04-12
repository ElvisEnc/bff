package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@lombok.Data
public class ExtractRequest {
    private Filter filters;

    @lombok.Data
    public static class Filter {
        private Pagination pagination;

        @Schema(example = "1", description = "1 Débito, 2 Crédito")
        private String type;

        private AmountRange amount;
    }

    @lombok.Data
    public static class Pagination {
        @Schema(example = "2023-11-22", description = "Fecha Inicio")
        @NotBlank(message = "startDate no puede estar en blanco")
        private String startDate;

        @Schema(example = "2023-12-31", description = "Fecha fin")
        @NotBlank(message = "endDate no puede estar en blanco")
        private String endDate;

        @Schema(example = "1", description = "Número de página")
        @NotNull(message = "Page es obligatorio")
        @Min(value = 1, message = "Page debe comenzar por lo menos en 1")
        private Integer page;

        @Schema(example = "10", description = "Cantidad de registros por página")
        @NotNull(message = "pageSize es obligatorio")
        @Min(value = 1, message = "pageSize debe comenzar por lo menos en 1")
        private Integer pageSize;
    }
}
