package bg.com.bo.bff.commons.enums.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum LoginSchemaName {
    ID_LOGIN("1", "IDLOGIN"),
    PERSON_ID_LOGIN("2", "PERSONIDLOGIN"),
    DNI_LOGIN("3", "DNILOGIN"),
    FINGERPRINT_BIOMETRICS("4", "BIOMETRIA"),
    FACIAL_BIOMETRICS("5", "BIOMETRIAFACIAL");

    private final String code;
    private final String name;
    public static Optional<LoginSchemaName> findByCode(String code) {
        return Arrays.stream(values())
                .filter(schema -> schema.code.equals(code))
                .findFirst();
    }
}
