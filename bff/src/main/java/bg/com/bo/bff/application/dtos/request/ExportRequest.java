package bg.com.bo.bff.application.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@lombok.Data
public class ExportRequest {
    @NotBlank
    @Pattern(regexp = "PDF|CSV", message = "El formato debe ser 'PDF' o 'CSV'")
    @Schema(example = "PDF", description = "Formato de exportación. PDF/CSV")
    private String format;

    @Valid
    private Filter filters;

    @lombok.Data
    public static class Filter {
        @NotBlank
        @Schema(example = "2023-11-22", description = "Fecha Inicio")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha de inicio debe estar en formato 'AAAA-MM-DD'")
        private String startDate;

        @NotBlank
        @Schema(example = "2023-12-31", description = "Fecha fin")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "La fecha de fin debe estar en formato 'AAAA-MM-DD'")
        private String endDate;
    }
}


