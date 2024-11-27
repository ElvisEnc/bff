package bg.com.bo.bff.models.payload.encryption;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class FirstLayerEncryptionAnonymousUserHandler implements IFirstLayerEncryptionHandler {
    private static final Logger logger = LogManager.getLogger(FirstLayerEncryptionAnonymousUserHandler.class.getName());
    private final IEncryptionService encryptionService;
    private String sessionEncryptedKeyHeader;
    private AnonymousUserEncryptInfo encryptInfo;

    public FirstLayerEncryptionAnonymousUserHandler(HttpServletRequest request, IEncryptionService encryptionService) {
        validate(request);
        this.encryptionService = encryptionService;
    }

    @Override
    public String decrypt() {
        PrivateKey appPrivateKey = this.encryptionService.getAnonymousAppPrivateKey(encryptInfo.getServerKeyId());
        try {
            return CipherUtils.decrypt(EncryptionAlgorithm.RSA, sessionEncryptedKeyHeader, appPrivateKey);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.INVALID_ENCRYPTED_DATA);
        }
    }

    @Override
    public String encrypt(PayloadKey payloadKey) {
        try {
            PublicKey publicUserKey = this.encryptionService.getAnonymousUserPublicKey(this.encryptInfo.getPublicUserKey());
            return CipherUtils.encrypt(EncryptionAlgorithm.RSA, Util.objectToString(payloadKey), publicUserKey);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.INVALID_USER_ENCRYPTION_KEY);
        }
    }

    private void validate(HttpServletRequest request) {
        this.sessionEncryptedKeyHeader = request.getHeader(Constants.SESSION_ENCRYPT_KEY_HEADER);
        String encryptInfoHeader = request.getHeader(Constants.USER_SESSION_ENCRYPT_INFO_HEADER);

        boolean validSessionEncryptedKeyHeader = sessionEncryptedKeyHeader != null && !sessionEncryptedKeyHeader.isEmpty();
        boolean validEncryptInfoHeader = encryptInfoHeader != null && !encryptInfoHeader.isEmpty();

        if (!validSessionEncryptedKeyHeader || !validEncryptInfoHeader)
            throw new GenericException(DefaultMiddlewareError.INVALID_ENCRYPT_INFO);

        this.encryptInfo = parseEncryptInfo(encryptInfoHeader);
    }

    private AnonymousUserEncryptInfo parseEncryptInfo(String encryptInfoHeader) {
        byte[] encodeInfoBytes = Base64.getDecoder().decode(Util.getEncodedBytes(encryptInfoHeader));
        String userEncodeInfo = new String(encodeInfoBytes);
        try {
            AnonymousUserEncryptInfo encodeInfo = Util.stringToObject(userEncodeInfo, AnonymousUserEncryptInfo.class);

            if (encodeInfo.getPublicUserKey() == null || encodeInfo.getServerKeyId() == null || encodeInfo.getPublicUserKey().isEmpty() || encodeInfo.getServerKeyId().isEmpty())
                throw new GenericException(DefaultMiddlewareError.INVALID_ENCODED_INFO);

            return encodeInfo;
        } catch (GenericException e) {
            throw (e);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.INVALID_ENCODED_INFO);
        }
    }
}
