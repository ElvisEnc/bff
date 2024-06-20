package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MaritalStatus {
    MARRIED("C"),
    SINGLE("S"),
    DIVORCED("D"),
    WIDOWED("V"),
    COMMON_LAW_UNION("U");
    private final String code;

}
