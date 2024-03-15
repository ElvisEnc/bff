package bg.com.bo.bff.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EncryptionAlgorithm {
    AES_256_CBC_PKCS5_PADDING("AES/CBC/PKCS5Padding", "AES", 256),
    RSA("RSA", "RSA", 4096);

    private final String code;
    private final String family;
    private final Integer keySize;
}
