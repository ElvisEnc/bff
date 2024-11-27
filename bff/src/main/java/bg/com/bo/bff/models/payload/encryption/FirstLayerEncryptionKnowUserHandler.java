package bg.com.bo.bff.models.payload.encryption;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class FirstLayerEncryptionKnowUserHandler implements IFirstLayerEncryptionHandler {
    private static final Logger logger = LogManager.getLogger(FirstLayerEncryptionKnowUserHandler.class.getName());
    private EncryptInfo encryptInfo;
    private String sessionEncryptedKeyHeader;
    private final IEncryptionService encryptionService;

    public FirstLayerEncryptionKnowUserHandler(HttpServletRequest request, IEncryptionService encryptionService) {
        validate(request);
        this.encryptionService = encryptionService;
    }

    private EncryptInfo parseEncryptInfo(String encodeInfoHeader) {
        byte[] encodeInfoBytes = Base64.getDecoder().decode(Util.getEncodedBytes(encodeInfoHeader));
        String userEncodeInfo = new String(encodeInfoBytes);
        try {
            EncryptInfo encodeInfo = Util.stringToObject(userEncodeInfo, EncryptInfo.class);

            if (encodeInfo.getPersonId() == null || encodeInfo.getUniqueId() == null)
                throw new GenericException(DefaultMiddlewareError.INVALID_ENCODED_INFO);

            return encodeInfo;
        } catch (GenericException e) {
            throw (e);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.INVALID_ENCODED_INFO);
        }
    }

    @Override
    public String decrypt() {
        PrivateKey appPrivateKey = this.encryptionService.getAppPrivateKey(encryptInfo);
        try {
            return CipherUtils.decrypt(EncryptionAlgorithm.RSA, sessionEncryptedKeyHeader, appPrivateKey);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.INVALID_ENCRYPTED_DATA);
        }
    }

    @Override
    public String encrypt(PayloadKey payloadKey) {
        PublicKey publicKey = this.encryptionService.getUserPublicKey(encryptInfo);
        try {
            return CipherUtils.encrypt(EncryptionAlgorithm.RSA, Util.objectToString(payloadKey), publicKey);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.INVALID_USER_ENCRYPTION_KEY);
        }
    }

    private void validate(HttpServletRequest request) {
        String encryptInfoHeader = request.getHeader(Constants.USER_SESSION_ENCRYPT_INFO_HEADER);
        sessionEncryptedKeyHeader = request.getHeader(Constants.SESSION_ENCRYPT_KEY_HEADER);

        boolean validSessionEncryptedKeyHeader = sessionEncryptedKeyHeader != null && !sessionEncryptedKeyHeader.isEmpty();
        boolean validEncodeInfoHeader = encryptInfoHeader != null && !encryptInfoHeader.isEmpty();

        if (!validSessionEncryptedKeyHeader || !validEncodeInfoHeader)
            throw new GenericException(DefaultMiddlewareError.INVALID_ENCRYPT_INFO);

        this.encryptInfo = parseEncryptInfo(encryptInfoHeader);
    }
}
