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
public class GenerateVoucherRequest {

    @NotBlank(message = "seatNumber no puede estar vacío.")
    @Schema(description = "seatNumber es necesario para generar el voucher.",
            example = "999000161")
    private String seatNumber;

    @NotBlank(message = "dateProcess no puede estar vacío.")
    @Schema(description = "dateProcess es necesario para generar el voucher.",
            example = "05/05/2025")
    private String dateProcess;
}
