package bg.com.bo.bff.application.dtos.response.own.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnAccountsResponse {
    @Schema(description = "Este es el Id de una cuenta")
    private String accountId;

    @Schema(description = "Este es el número de la cuenta")
    private String accountNumber;

    @Schema(description = "Este es el nombre del cliente")
    private String clientName;

    @Schema(description = "Este es el código del cliente")
    private String clientCode;

    @Schema(description = "Este es el accountHolderCode")
    private String accountHolderCode;

    @Schema(description = "Este es el código de moneda de la cuenta")
    private String currencyCode;

    @Schema(description = "Este es la descripción de la moneda")
    private String currencyDescription;

    @Schema(description = "Este es la descripción del producto")
    private String productDescription;

    @Schema(description = "Este es la descripción corta del producto")
    private String productShortDescription;

    @Schema(description = "Este es el código del manejo de la cuenta")
    private String accountManagementCode;

    @Schema(description = "Este es el abreviado del tipo de cuenta")
    private String accountType;

    @Schema(description = "Este es el balance de la cuenta")
    private BigDecimal availiableBalance;

    @Schema(description = "Este es la descripción del manejo de la cuenta")
    private String accountManagementDescription;

    @Schema(description = "Este es la fecha de apertura de la cuenta")
    private String openingDate;

    @Schema(description = "Este es la fecha del último movimiento")
    private String dateOfLastMovement;

    @Schema(description = "Este es el total del balance de la cuenta")
    private BigDecimal totalBalance;

    @Schema(description = "Este es el monto de fondos pignorados")
    private BigDecimal pledgeFounds;

    @Schema(description = "Este es el monto de depósitos pendientes")
    private BigDecimal pendingDeposits;

    @Schema(description = "Este es el código del estado de la cuenta")
    private String statusCode;

    @Schema(description = "Este es la decripción del estado de la cuenta")
    private String statusDescription;

    @Schema(description = "Este es el código de la sucursal donde se apeturó la cuenta")
    private String branchCode;

    @Schema(description = "Este es el nombre de la sucursal")
    private String branchDescription;

    @Schema(description = "Este es el código del departamento de apertura de la cuenta")
    private String departamentCode;

    @Schema(description = "Este es el nombre del departamento")
    private String departamentDescription;

    @Schema(description = "Este es el código del tipo de uso de la cuenta")
    private String accountUsage;

    @Schema(description = "Este es la descripción del tipo de uso de la cuenta")
    private String accountUsageDescription;
}
