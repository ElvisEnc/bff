package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectNameMW {
    LOGIN_MANAGER("/login-manager", "LOGIN MANAGER", "Secret"),
    OWN_ACCOUNT_MANAGER("/own-accounts-manager", "OWN ACCOUNT MANAGER", "Secret"),
    TRANSFER_MANAGER("/transaction-manager", "TRANSACTION MANAGER", "Authorization"),
    THIRD_ACCOUNTS("/third-accounts-manager", "THIRD ACCOUNTS MANAGER", "Secret"),
    ACH_ACCOUNTS("/ach-account-manager", "ACH ACCOUNTS MANAGER", "Secret");

    private final String name;
    private final String description;
    private final String headerKey;
}
