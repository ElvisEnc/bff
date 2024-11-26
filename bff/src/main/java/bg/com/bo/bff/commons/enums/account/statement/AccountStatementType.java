package bg.com.bo.bff.commons.enums.account.statement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatementType {
    DEBITO("1", "D"),
    CREDITO("2","C");

    private final String code;
    private final String value;

    public static String getValueByCode(String code) {
        for (AccountStatementType type : values()) {
            if (type.code.equals(code)) {
                return type.getValue();
            }
        }
        return null;
    }
}
