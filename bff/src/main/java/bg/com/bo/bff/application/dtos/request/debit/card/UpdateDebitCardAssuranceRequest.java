package bg.com.bo.bff.application.dtos.request.debit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDebitCardAssuranceRequest {
    @NotBlank(message = "el email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    @Schema(description = "correo electrónico", example = "123@dominio.org")
    private String email;

    @NotNull(message = "openingRequestFlow no puede estar en blanco")
    @Schema(description = "booleano si viene del flujo de apertura", example = "false")
    private Boolean openingRequestFlow;
    @NotNull
    @NotBlank
    @Pattern(regexp = "\\d+", message = "openingRequestNumber debe contener solo números")
    @Schema(description = "número de solicitud del flujo de apertura", example = "0")
    private String openingRequestNumber;
}
