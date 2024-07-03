package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.commons.enums.EncryptionAlgorithm;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.response.GenericControllerErrorResponse;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;
import bg.com.bo.bff.providers.dtos.response.encryption.UserEncryptionKeys;
import bg.com.bo.bff.providers.interfaces.IEncryptionProvider;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;

@Service
public class EncryptionService implements IEncryptionService {
    private IEncryptionProvider encryptionProvider;

    @Autowired
    public EncryptionService(IEncryptionProvider encryptionProvider) {
        this.encryptionProvider = encryptionProvider;
    }

    public PublicKey getAppPublicKey(EncryptInfo encodeInfo) {
        try {
            UserEncryptionKeys userEncryptionKeys = encryptionProvider.getEncryptionKeys(encodeInfo);
            return CipherUtils.createPublicKey(EncryptionAlgorithm.RSA, userEncryptionKeys.getAppPublicKey());
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR, e);
        }
    }

    public PrivateKey getAppPrivateKey(EncryptInfo encodeInfo) {
        try {
            UserEncryptionKeys userEncryptionKeys = encryptionProvider.getEncryptionKeys(encodeInfo);
            return CipherUtils.createPrivateKey(EncryptionAlgorithm.RSA, userEncryptionKeys.getAppPrivateKey());
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR, e);
        }
    }

    public PublicKey getUserPublicKey(EncryptInfo encodeInfo) {
        try {
            UserEncryptionKeys userEncryptionKeys = encryptionProvider.getEncryptionKeys(encodeInfo);
            return CipherUtils.createPublicKey(EncryptionAlgorithm.RSA, userEncryptionKeys.getUserPublicKey());
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR, e);
        }
    }
}
