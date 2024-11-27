package bg.com.bo.bff.services.interfaces;

import bg.com.bo.bff.models.payload.encryption.EncryptionKey;
import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;

import java.security.*;
import java.security.spec.InvalidKeySpecException;

public interface IEncryptionService {
    PublicKey getAppPublicKey(EncryptInfo encodeInfo);

    PrivateKey getAppPrivateKey(EncryptInfo encodeInfo);

    PublicKey getUserPublicKey(EncryptInfo encodeInfo);

    PrivateKey getAnonymousAppPrivateKey(String anonymousSessionEncryptedKeyHeader);

    PublicKey getAnonymousUserPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException;

    EncryptionKey getAnonymousKey();

    KeyPair createKeys() throws NoSuchAlgorithmException;
}
