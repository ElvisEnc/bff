package bg.com.bo.bff.application.dtos.response.certifications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationAccountsResponse {

    private String accountId;
    private String accountNumber;
    private String clientName;
    private String product;
    private String descState;
    private String descHandle;
    private String descCurrency;
    private String currentBalance;
    private String availableBalance;
    private String pledgedDFunds;
    private String depnoconf;
    private String currencyCode;
    private String currency;
    private String descAccountType;
    private String accountTypeCode;

}

