package bg.com.bo.bff.commons.enums.config.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EncryptionAlgorithm {
    AES_256_CBC_PKCS5_PADDING("AES/CBC/PKCS5Padding", "AES", 256, null),
    RSA("RSA", "RSA", 4096, null),
    AES_256_GCM_NO_PADDING("AES/GCM/NoPadding", "AES", 256, 128);

    private final String code;
    private final String family;
    private final Integer keySize;
    private final Integer tagLen;
}
