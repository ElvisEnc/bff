package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppCodeResponseNet {
    SUCCESS_CODE_STRING("COD000"),
    SUCCESS_CODE_NUMBER("0");

    private final String value;
}
