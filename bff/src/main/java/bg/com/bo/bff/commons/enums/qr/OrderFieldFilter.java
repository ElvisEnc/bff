package bg.com.bo.bff.commons.enums.qr;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderFieldFilter {
    REGISTRATION_DATE("REGISTRATION_DATE"),
    EXPIRATION_DATE("EXPIRATION_DATE"),
    AMOUNT("AMOUNT");

    private final String code;

    public static OrderFieldFilter findByDescription(String code) {
        return Arrays.stream(OrderFieldFilter.values())
                .filter(value -> value.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
