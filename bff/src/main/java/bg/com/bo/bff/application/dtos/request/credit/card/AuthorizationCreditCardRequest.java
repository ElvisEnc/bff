package bg.com.bo.bff.application.dtos.request.credit.card;

import bg.com.bo.bff.application.dtos.request.commons.PeriodRequest;
import bg.com.bo.bff.commons.annotations.credit.card.ValidRequestType;
import bg.com.bo.bff.commons.annotations.credit.card.ValidType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationCreditCardRequest {
    @NotBlank
    @Pattern(regexp = "13-\\d{2}-10-\\d{6}", message = "Valor inválido para cmsAccountNumber")
    @Schema(description = "número compuesto de la Cuenta", example = "123456")
    private String cmsAccount;

    @NotBlank
    @Pattern(regexp = "13-\\d{2}-10-\\d{10}", message = "Valor inválido para cmsCardNumber")
    @Schema(description = "número compuesto de la Tarjeta", example = "123456")
    private String cmsCard;

    @DecimalMin(value = "0", inclusive = false, message = "El monto debe ser mayor que cero")
    @Digits(integer = 12, fraction = 2, message = "El monto debe tener hasta 12 dígitos enteros y 2 decimales")
    @Schema(description = "monto", example = "10.00")
    private BigDecimal amount;

    @Valid
    private PeriodRequest period;

    @NotBlank
    @ValidType
    @Schema(description = "Tipo de habilitación", example = "H")
    private String type;

    @NotBlank
    @ValidRequestType
    @Schema(description = "Tipo de habilitación: Internet o Especiales", example = "I")
    private String requestType;
}
