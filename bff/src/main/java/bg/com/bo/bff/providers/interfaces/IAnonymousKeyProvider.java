package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import bg.com.bo.bff.models.payload.encryption.EncryptionKey;

public interface IAnonymousKeyProvider {
    EncryptionKey getAnonymousKey(EncryptionAlgorithm encryptionAlgorithm);

    EncryptionKey getAnonymousKey(String id);
}
