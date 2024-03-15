package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.models.EncodeInfo;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface IEncryptionService {
    PublicKey getAppPublicKey(EncodeInfo encodeInfo);

    PrivateKey getAppPrivateKey(EncodeInfo encodeInfo);

    PublicKey getUserPublicKey(EncodeInfo encodeInfo);
}
