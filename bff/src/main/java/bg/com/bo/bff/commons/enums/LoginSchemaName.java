package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginSchemaName {
    IDLOGIN("1", "IDLOGIN"), PERSONIDLOGIN("2", "PERSONIDLOGIN"), DNILOGIN("3", "DNILOGIN");

    private final String code;
    private final String name;
}
