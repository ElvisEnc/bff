package bg.com.bo.bff.application.dtos.request.debit.card;

import bg.com.bo.bff.commons.annotations.debit.card.ValidCardNumber;
import bg.com.bo.bff.commons.annotations.debit.card.ValidPin;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivateDebitCardRequest {
    @NotBlank
    @Schema(description = "base64 de la foto tomada", example = "s5df46s5d4f6sd54f6sdf4")
    private String verificationPicture;

    @NotNull
    @ValidCardNumber
    @Schema(description = "número de la tarjeta de débito", example = "4200560090901100")
    private Long cardNumber;

    @NotNull
    @ValidPin
    @Schema(description = "pin de la tarjeta de débito", example = "1234")
    private Integer pin;
}
