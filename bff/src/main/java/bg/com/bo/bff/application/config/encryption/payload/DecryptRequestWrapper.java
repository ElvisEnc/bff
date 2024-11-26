package bg.com.bo.bff.application.config.encryption.payload;

import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.payload.encryption.AesPayloadResolver;
import bg.com.bo.bff.providers.dtos.request.encryption.EncryptInfo;
import bg.com.bo.bff.models.payload.encryption.EncryptionPayload;
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.*;
import java.io.*;
import java.security.*;
import java.util.Base64;

public class DecryptRequestWrapper extends HttpServletRequestWrapper {
    private final IEncryptionService encryptionService;
    private final String body;

    @lombok.Getter
    private EncryptInfo encodeInfo;
    private EncryptionPayload encryptionPayloadDecrypted;
    private final String payloadAlgorithm;

    private static final Logger logger = LogManager.getLogger(DecryptRequestWrapper.class.getName());

    public DecryptRequestWrapper(HttpServletRequest request, IEncryptionService encryptionService, String payloadAlgorithm) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        super(request);
        this.payloadAlgorithm = payloadAlgorithm;
        this.encryptionService = encryptionService;
        this.processPayload(request);
        this.body = this.encryptionPayloadDecrypted.getBody();
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Util.getEncodedBytes(body));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    private void processPayload(HttpServletRequest request) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        // Validate headers.
        String encodeInfoHeader = request.getHeader(Constants.BFF_ENCODE_INFO_HEADER);
        String sessionEncryptedKeyHeader = request.getHeader(Constants.SESSION_ENCRYPTED_KEY_HEADER);

        if (encodeInfoHeader == null || encodeInfoHeader.isEmpty())
            throw new GenericException(DefaultMiddlewareError.NO_ENCODE_INFO);

        if (sessionEncryptedKeyHeader == null || sessionEncryptedKeyHeader.isEmpty())
            throw new GenericException(DefaultMiddlewareError.NO_ENCODE_INFO);

        parseEncryptInfo(encodeInfoHeader);

        String payloadKeys = getPayloadKey(sessionEncryptedKeyHeader);

        EncryptionPayload encryptedPayload = getRawPayload(request);

        this.encryptionPayloadDecrypted = AesPayloadResolver.instance(payloadAlgorithm).decrypt(payloadKeys, encryptedPayload);
    }

    /**
     * Obtiene los datos de desencriptacion para payload.
     *
     * @param sessionEncryptedKeyHeader
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws IOException
     */
    private String getPayloadKey(String sessionEncryptedKeyHeader) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        PrivateKey appPrivateKey = this.encryptionService.getAppPrivateKey(encodeInfo);
        return CipherUtils.decrypt(EncryptionAlgorithm.RSA, sessionEncryptedKeyHeader, appPrivateKey);
    }

    /**
     * Obtiene el payload del request en el formato de la clase {@link EncryptionPayload EncryptionPayload} con los datos raw.
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static EncryptionPayload getRawPayload(HttpServletRequest request) throws IOException {
        String rawPayload = Util.getPayload(request);
        if (rawPayload.isEmpty())
            return null;
        return Util.stringToObject(rawPayload, EncryptionPayload.class);
    }

    /**
     * Obtiene los datos necesarios del usuario para con los cuales poder obtener los datos necesarios para encriptar.
     *
     * @param encodeInfoHeader
     * @throws IOException
     */
    private void parseEncryptInfo(String encodeInfoHeader) throws IOException {
        byte[] encodeInfoBytes = Base64.getDecoder().decode(Util.getEncodedBytes(encodeInfoHeader));
        String userEncodeInfo = new String(encodeInfoBytes);
        try {
            encodeInfo = Util.stringToObject(userEncodeInfo, EncryptInfo.class);

            if (encodeInfo.getPersonId() == null || encodeInfo.getUniqueId() == null)
                throw new GenericException(DefaultMiddlewareError.INVALID_ENCODE_INFO);
        } catch (GenericException e) {
            throw (e);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(DefaultMiddlewareError.INVALID_ENCODE_INFO);
        }
    }
}
