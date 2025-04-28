package bg.com.bo.bff.application.dtos.request.credit.card;

import bg.com.bo.bff.commons.annotations.OnlyNumber;
import bg.com.bo.bff.commons.annotations.ValidText;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashAdvanceRequest {
    @NotBlank
    @Pattern(regexp = "13-\\d{2}-10-\\d{6}", message = "Invalid value for cmsAccountNumber")
    @Schema(description = "número compueto de la Cuenta", example = "123456")
    private String cmsAccount;

    @NotBlank
    @Pattern(regexp = "13-\\d{2}-10-\\d{10}", message = "Invalid value for cms card number")
    @Schema(description = "número compueto de la Tarjeta", example = "123456")
    private String cmsCard;

    @NotBlank
    @NotNull(message = "no puede ser nulo.")
    @Schema(description = "panNumber es necesario", example = "4099-11XX-XXXX-2314")
    private String panNumber;

    @NotBlank
    @NotNull(message = "no puede ser nulo.")
    @Schema(description = "dueDate necesario", example = "01/12/2024")
    private String dueDate;

    @NotBlank
    @OnlyNumber
    @Schema(description = "Id de la cuenta propia", example = "123456789")
    private String accountId;

    @DecimalMin(value = "0", inclusive = false, message = "El monto debe ser mayor que cero")
    @Digits(integer = 12, fraction = 2, message = "El monto debe tener hasta 12 dígitos enteros y 2 decimales")
    @Schema(description = "monto", example = "10.00")
    private BigDecimal amount;

    @NotBlank
    @ValidText
    @Size(min = 3, max = 50, message = "La descripción debe tener entre 3 y 50 caracteres.")
    @Schema(description = "descripción", example = "Test")
    private String description;
}
