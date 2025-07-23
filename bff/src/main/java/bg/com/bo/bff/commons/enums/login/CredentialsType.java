package bg.com.bo.bff.commons.enums.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CredentialsType {
    PASSWORD(1), BIOMETRIC(2);

    private final int value;
}

