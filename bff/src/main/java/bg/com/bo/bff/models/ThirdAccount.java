package bg.com.bo.bff.models;

@lombok.Data
public class ThirdAccount {
    private String id;
    private String accountId;
    private String accountNumber;
    private String currencyCode;
    private String currencyAcronym;
    private String accountType;
    private String accountTypeAbbreviation;
    private String clientName;
    private String accountAliases;
    private String isFavorite;
}
