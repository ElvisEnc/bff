package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.models.payload.encryption.EncryptionKey;
import bg.com.bo.bff.providers.interfaces.IAnonymousKeyProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;
import bg.com.bo.bff.providers.dtos.response.encryption.UserEncryptionKeys;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

@Service
public class EncryptionService implements IEncryptionService {
    private final ILoginMiddlewareProvider loginProvider;
    private final IAnonymousKeyProvider anonymousKeyProvider;
    private final EncryptionAlgorithm encryptionAlgorithm;
    private static final Logger logger = LogManager.getLogger(EncryptionService.class.getName());

    @Autowired
    public EncryptionService(ILoginMiddlewareProvider loginProvider, IAnonymousKeyProvider anonymousKeyProvider) {
        this.loginProvider = loginProvider;
        this.anonymousKeyProvider = anonymousKeyProvider;
        this.encryptionAlgorithm = EncryptionAlgorithm.RSA;
    }

    @Override
    public PublicKey getAppPublicKey(EncryptInfo encodeInfo) {
        try {
            UserEncryptionKeys userEncryptionKeys = loginProvider.getEncryptionKeys(encodeInfo);
            return CipherUtils.createPublicKey(EncryptionAlgorithm.RSA, userEncryptionKeys.getAppPublicKey());
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public PrivateKey getAppPrivateKey(EncryptInfo encodeInfo) {
        try {
            UserEncryptionKeys userEncryptionKeys = loginProvider.getEncryptionKeys(encodeInfo);
            return CipherUtils.createPrivateKey(EncryptionAlgorithm.RSA, userEncryptionKeys.getAppPrivateKey());
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public PublicKey getUserPublicKey(EncryptInfo encodeInfo) {
        try {
            UserEncryptionKeys userEncryptionKeys = loginProvider.getEncryptionKeys(encodeInfo);
            return CipherUtils.createPublicKey(EncryptionAlgorithm.RSA, userEncryptionKeys.getUserPublicKey());
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public PrivateKey getAnonymousAppPrivateKey(String id) {
        try {
            EncryptionKey key = this.anonymousKeyProvider.getAnonymousKey(id);
            if (key == null)
                throw new GenericException(DefaultMiddlewareError.INVALID_ENCRYPTION_KEY);

            return CipherUtils.createPrivateKey(EncryptionAlgorithm.RSA, key.getPrivateKey());
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            logger.info(e);
            throw new HandledException(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public PublicKey getAnonymousUserPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return CipherUtils.createPublicKey(EncryptionAlgorithm.RSA, publicKey);
    }

    @Override
    public EncryptionKey getAnonymousKey() {
        return anonymousKeyProvider.getAnonymousKey(this.encryptionAlgorithm);
    }

    @Override
    public KeyPair createKeys() throws NoSuchAlgorithmException {
        return CipherUtils.createKeys(encryptionAlgorithm);
    }
}
