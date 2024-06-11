package bg.com.bo.bff.application.dtos.response.debit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebitCard {
    @Schema(description = "id de la tarjeta de débito")
    private String id;

    @Schema(description = "número de la tarjeta de débito")
    private String cardNumber;

    @Schema(description = "nombre de la tarjeta de débito")
    private String holderName;

    @Schema(description = "fecha de expiración  de la tarjeta de débito")
    private String expiryDate;

    @Schema(description = "estado de la tarjeta de débito")
    private String status;
}
