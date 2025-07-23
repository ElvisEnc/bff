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
public class DCDetailResponse {
    @Schema(description = "Número de la tarjeta", example = "1234567890")
    private String cardNumber;

    @Schema(description = "Nombre del titular", example = "John Doe")
    private String holderName;

    @Schema(description = "Fecha de expiración", example = "2024-08-04")
    private String expirationDate;

    @Schema(description = "Estado de la tarjeta", example = "A | C | B")
    private String status;

    @Schema(description = "Estado de la tarjeta", example = "Activa | Cancelada | Bloqueada")
    private String statusDescription;

    @Schema(description = "Cuenta con seguro", example = "1")
    private Boolean assured;

    @Schema(description = "Fecha de expiración del limite de la tarjeta de débito")
    private String limitExpirationDate;

    @Schema(description = "Monto limite de la tarjeta de débito")
    private String limitAmount;

    @Schema(description = "Cantidad limite de la tarjeta de débito")
    private String limitNumber;
}
