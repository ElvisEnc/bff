package bg.com.bo.bff.application.dtos.request.credit.card;

import bg.com.bo.bff.application.dtos.request.transfer.AmountTransfer;
import bg.com.bo.bff.application.dtos.request.transfer.DataTransfer;
import bg.com.bo.bff.application.dtos.request.transfer.TargetAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayCreditCardRequest {
    @Valid
    @NotNull
    private TargetAccount targetAccount;

    @Valid
    @NotNull
    private AmountTransfer amount;

    @NotBlank
    @Pattern(regexp = "^(TC|TPP)$", message = "El tipo de transacción debe ser TC o TPP")
    @Schema(description = "tipo de transacción. TC=tarjeta de crédito TPP=tarjeta prepaga", example = "TC")
    private String transactionType;

    @Valid
    @NotNull
    private DataTransfer data;
}
