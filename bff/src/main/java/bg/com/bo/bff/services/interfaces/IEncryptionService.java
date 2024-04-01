package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.models.EncryptInfo;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface IEncryptionService {
    PublicKey getAppPublicKey(EncryptInfo encodeInfo);

    PrivateKey getAppPrivateKey(EncryptInfo encodeInfo);

    PublicKey getUserPublicKey(EncryptInfo encodeInfo);
}
