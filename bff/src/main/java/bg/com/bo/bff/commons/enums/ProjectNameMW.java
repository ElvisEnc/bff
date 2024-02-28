package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectNameMW {

    TRANSFER_MANAGER("/transaction-manager", "TRANSACTION MANAGER");

    private final String name;
    private final String description;
}
