package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.models.EncodeInfo;
import bg.com.bo.bff.models.UserEncryptionKeys;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public interface IEncryptionProvider {
    UserEncryptionKeys getEncryptionKeys(EncodeInfo encodeInfo);

    KeyPair createKeys() throws NoSuchAlgorithmException;
}
