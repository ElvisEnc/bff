package bg.com.bo.bff.application.dtos.response.certifications;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationAccountsResponse {

    @Schema(description = "Identificador de la cuenta")
    private String accountId;

    @Schema(description = "Nro de la cuenta")
    private String accountNumber;

    @Schema(description = "Nombre del cliente")
    private String clientName;

    @Schema(description = "Tipo de producto")
    private String product;

    @Schema(description = "Descripcion del estado de la cuenta")
    private String descState;

    @Schema(description = "Descripcion del mandejo de la cuenta")
    private String descHandle;

    @Schema(description = "Descripcion de la moneda de la cuenta")
    private String descCurrency;

    @Schema(description = "Balance de la cuenta")
    private String currentBalance;

    @Schema(description = "Saldo disponible en cuenta")
    private String availableBalance;

    @Schema(description = "Fondos pignorados")
    private String pledgedDFunds;

    @Schema(description = "depnoconf")
    private String depnoconf;

    @Schema(description = "Codigo de la moneda de la cuenta")
    private String currencyCode;

    @Schema(description = "Moneda de la cuenta en texto")
    private String currency;

    @Schema(description = "Descirpcion del  tipo de la cuenta")
    private String descAccountType;

    @Schema(description = "Codigo del tipo de cuenta")
    private String accountTypeCode;
}

