package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@lombok.Data
public class ExportRequest {
    @NotBlank
    @Schema(example = "PDF", description = "Formato de exportación. PDF/CSV")
    private String format;
    private Filter filters;

    @lombok.Data
    public static class Filter {
        @NotBlank
        @Schema(example = "2023-11-22", description = "Fecha Inicio")
        private String startDate;

        @NotBlank
        @Schema(example = "2023-12-31", description = "Fecha fin")
        private String endDate;
    }
}


