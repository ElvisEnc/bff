package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectNameMW {
    LOGIN_MANAGER("/login-manager", "LOGIN MANAGER", "secret"),
    TRANSFER_MANAGER("/transaction-manager", "TRANSACTION MANAGER", "Authorization");

    private final String name;
    private final String description;
    private final String headerKey;
}
