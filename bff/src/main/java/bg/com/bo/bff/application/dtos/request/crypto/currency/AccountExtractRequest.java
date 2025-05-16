package bg.com.bo.bff.application.dtos.request.crypto.currency;

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
public class AccountExtractRequest {
    @NotBlank(message = "accountNumber no puede ser vacío.")
    @Schema(description = "accountNumber permite realizar la busqueda de los extractos.",
            example = "558745")
    private String accountNumber;

    @NotBlank(message = "startDate no puede estar vacío.")
    @Schema(description = "startDate es necesario para realizar la busqueda.",
            example = "05/05/2025")
    private String startDate;

    @NotBlank(message = "endDate no puede estar vacío.")
    @Schema(description = "endDate es necesario para realizar la busqueda.",
            example = "05/05/2025")
    private String endDate;

    @Schema(description = "startPage es necesario para realizar la paginacion.",
            example = "1")
    private int startPage;

    @Schema(description = "endPage es necesario para realizar la paginacion.",
            example = "1000")
    private int endPage;
}
