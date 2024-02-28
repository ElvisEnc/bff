package bg.com.bo.bff.models;

import io.swagger.v3.oas.annotations.media.Schema;

@lombok.Data
public class Account {
    @Schema(example = "4355307", description = "Este es el Id de una cuenta")
    private String accountId;
    @Schema(example = "1310325715", description = "Este es el número de la cuenta")
    private String accountNumber;
    @Schema(example = "Persona Natural", description = "Este es el nombre del cliente")
    private String clientName;
    @Schema(example = "4123455", description = "Este es el código del cliente")
    private String clientCode;
    @Schema(example = " ", description = "Este es el accountHolderCode")
    private String accountHolderCode;
    @Schema(example = "068", description = "Este es el código de moneda de la cuenta")
    private String currencyCode;
    @Schema(example = "Bs", description = "Este es la descripción de la moneda")
    private String currencyDescription;
    @Schema(example = "CAJA DE AHORRO GANADOBLE", description = "Este es la descripción del producto")
    private String productDescription;
    @Schema(example = "I", description = "Este es el código del manejo de la cuenta")
    private String accountManagementCode;
    @Schema(example = "CA", description = "Este es el abreviado del tipo de cuenta")
    private String accountType;
    @Schema(example = "123.50", description = "Este es el balance de la cuenta")
    private Double availiableBalance;
    @Schema(example = "Individual", description = "Este es la descripción del manejo de la cuenta")
    private String accountManagementDescription;
    @Schema(example = "2019-05-25", description = "Este es la fecha de apertura de la cuenta")
    private String openingDate;
    @Schema(example = "2019-08-31", description = "Este es la fecha del último movimiento")
    private String dateOfLastMovement;
    @Schema(example = "525.23", description = "Este es el total del balance de la cuenta")
    private Double totalBalance;
    @Schema(example = "456.50", description = "Este es el monto de fondos pignorados")
    private Double pledgeFounds;
    @Schema(example = "0", description = "Este es el monto de depósitos pendientes")
    private Double pendingDeposits;
    @Schema(example = " ", description = "Este es el código del estado de la cuenta")
    private String statusCode;
    @Schema(example = "SIN BLOQUEO", description = "Este es la decripción del estado de la cuenta")
    private String statusDescription;
    @Schema(example = "712", description = "Este es el código de la sucursal donde se apeturó la cuenta")
    private String branchCode;
    @Schema(example = "GRIGOTA", description = "Este es el nombre de la sucursal")
    private String branchDescription;
    @Schema(example = "7", description = "Este es el código del departamento de apertura de la cuenta")
    private String departamentCode;
    @Schema(example = "SANTA CRUZ", description = "Este es el nombre del departamento")
    private String departamentDescription;
    @Schema(example = "T", description = "Este es el código del tipo de uso de la cuenta")
    private String accountUsage;
    @Schema(example = "Todo Uso", description = "Este es la descripción del tipo de uso de la cuenta")
    private String accountUsageDescription;
}
