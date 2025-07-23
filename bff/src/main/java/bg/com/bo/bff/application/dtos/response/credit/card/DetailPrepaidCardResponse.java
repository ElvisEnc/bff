package bg.com.bo.bff.application.dtos.response.credit.card;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailPrepaidCardResponse {
    @Schema(description = "id de la tarjeta")
    private String cardId;

    @Schema(description = "cms de la cuenta")
    private String cmsAccount;

    @Schema(description = "número de la tarjeta")
    private String cardNumber;

    @Schema(description = "nombre del titular")
    private String holderName;

    @Schema(description = "código del producto")
    private String productCode;

    @Schema(description = "producto")
    private String product;

    @Schema(description = "código de moneda")
    private String currency;

    @Setter
    @Schema(description = "Monto disponible")
    private BigDecimal availableAmount;

    @Schema(description = "fecha de ultima recarga")
    private String lastRechargeDate;

    @Schema(description = "fecha de registro")
    private String registrationDate;

    @Schema(description = "fecha de vencimiento")
    private String dueDate;

    @Schema(description = "estado de la tarjeta")
    private String cardStatus;

    @Schema(description = "estado de la cuenta")
    private String accountStatus;

    @Schema(description = "código de plaza")
    private String placeCode;

    @Schema(description = "plaza")
    private String place;

    @Schema(description = "seguro")
    private String insurance;

    @Schema(description = "límite minimo")
    private String limitMin;

    @Schema(description = "límite máximo")
    private String limitMax;
}
