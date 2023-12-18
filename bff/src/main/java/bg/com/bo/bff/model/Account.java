package bg.com.bo.bff.model;

import io.swagger.v3.oas.annotations.media.Schema;

@lombok.Data
public class Account {
    @Schema(example = "4355307", description = "Este es el Id de una cuenta")
    private String accountId;
    @Schema(example = "1310325715", description = "Este es el número de la cuenta")
    private String accountNumber;
    private String clientName;
    private String clientCode;
    private String accountHolderCode;
    private String currencyCode;
    private String currencyDescription;
    @Schema(example = "CAJA DE AHORRO GANADOBLE", description = "Este es la descripción del producto")
    private String productDescription;
    private String accountManagementCode;
    private String accountType;
    @Schema(example = "123.50", description = "Este es el balance de la cuenta")
    private Integer availiableBalance;
    private String accountManagementDescription;
    private String openingDate;
    private String dateOfLastMovement;
    @Schema(example = "525.23", description = "Este es el total del balance de la cuenta")
    private Integer totalBalance;
    @Schema(example = "0", description = "")
    private Integer pledgeFounds;
    private Integer pendingDeposits;
    private String statusCode;
    private String statusDescription;
    private String branchCode;
    private String branchDescription;
    private String departamentCode;
    private String departamentDescription;
    private String accountUsage;
    private String accountUsageDescription;
}
