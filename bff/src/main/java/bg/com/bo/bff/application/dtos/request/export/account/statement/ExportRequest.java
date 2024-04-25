package bg.com.bo.bff.application.dtos.request.export.account.statement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportRequest {
    @NotBlank
    @Pattern(regexp = "PDF|CSV", message = "El formato debe ser 'PDF' o 'CSV'")
    @Schema(example = "PDF", description = "Formato de exportación. PDF/CSV")
    private String format;

    @Valid
    private ExportFilter filters;
}


