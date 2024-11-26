package bg.com.bo.bff.commons.enums.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
