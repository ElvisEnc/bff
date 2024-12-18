package bg.com.bo.bff.commons.enums.config.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EncryptionHeaders {
    SESSION_ENCRYPT_KEY_HEADER("Bff-SEK"),
    ENCRYPT_INFO_HEADER("Bff-EI"),
    ENCRYPTION_EXCLUDED_KEY_HEADER("Bff-EEK");
    private final String code;

}
