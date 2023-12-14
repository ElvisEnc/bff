package bg.com.bo.bff.model;

@lombok.Data
public class Account {
    private String accountId;
    private String accountNumber;
    private String clientName;
    private String clientCode;
    private String accountHolderCode;
    private String currencyCode;
    private String currencyDescription;
    private String productDescription;
    private String accountManagementCode;
    private String accountType;
    private Integer availiableBalance;
    private String accountManagementDescription;
    private String openingDate;
    private String dateOfLastMovement;
    private Integer totalBalance;
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
