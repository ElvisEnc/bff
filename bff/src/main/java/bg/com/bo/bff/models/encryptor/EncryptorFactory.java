package bg.com.bo.bff.models.encryptor;

import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import org.apache.commons.lang3.NotImplementedException;

public class EncryptorFactory {

    private EncryptorFactory() {
    }

    public static IEncryptor instance(String algorithm) {
        EncryptionAlgorithm encryptionAlgorithm = EncryptionAlgorithm.valueOf(algorithm);

        return switch (encryptionAlgorithm) {
            case AES_256_CBC_PKCS5_PADDING -> AesCbcEncryptor.instance();
            case AES_256_GCM_NO_PADDING -> AesGcmEncryptor.instance();
            default -> throw new NotImplementedException();
        };
    }
}
