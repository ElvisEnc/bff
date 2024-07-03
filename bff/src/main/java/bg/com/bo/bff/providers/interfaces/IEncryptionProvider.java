package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;
import bg.com.bo.bff.providers.dtos.response.encryption.UserEncryptionKeys;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public interface IEncryptionProvider {
    UserEncryptionKeys getEncryptionKeys(EncryptInfo encodeInfo) throws IOException;

    KeyPair createKeys() throws NoSuchAlgorithmException;
}
