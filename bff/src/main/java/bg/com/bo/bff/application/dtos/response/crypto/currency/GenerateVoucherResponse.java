package bg.com.bo.bff.application.dtos.response.crypto.currency;

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
public class GenerateVoucherResponse {
    @NotBlank(message = "accountNumber no puede ser vacío.")
    @Schema(description = "accountNumber permite realizar la busqueda de los extractos.",
            example = "558745")
    private String accountNumber;

    @NotBlank(message = "accountNumber no puede ser vacío.")
    @Schema(description = "accountNumber permite realizar la busqueda de los extractos.",
            example = "558745")
    private String date;
}
