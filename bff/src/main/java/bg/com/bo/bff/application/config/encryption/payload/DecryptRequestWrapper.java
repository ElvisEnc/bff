package bg.com.bo.bff.application.config.encryption.payload;

import bg.com.bo.bff.commons.enums.EncryptionAlgorithm;
import bg.com.bo.bff.application.exceptions.NotEncodedInfoException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.EncodeInfo;
import bg.com.bo.bff.models.PayloadKey;
import bg.com.bo.bff.services.interfaces.IEncryptionService;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class DecryptRequestWrapper extends HttpServletRequestWrapper {
    private final String body;
    @Getter
    private EncodeInfo encodeInfo;

    private final IEncryptionService encryptionService;

    public DecryptRequestWrapper(HttpServletRequest request, IEncryptionService encryptionService) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeySpecException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        super(request);
        this.encryptionService = encryptionService;
        this.body = this.decryptPayload(request);
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
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

    private String decryptPayload(HttpServletRequest request) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        // Validate headers.
        String encodeInfoHeader = request.getHeader(Constants.BFF_ENCODE_INFO_HEADER);
        String sessionEncryptedKeyHeader = request.getHeader(Constants.SESSION_ENCRYPTED_KEY_HEADER);

        if (encodeInfoHeader == null || encodeInfoHeader.isEmpty())
            throw new NotEncodedInfoException();

        if (sessionEncryptedKeyHeader == null || sessionEncryptedKeyHeader.isEmpty())
            throw new NotEncodedInfoException();

        // Parse headers data
        byte[] encodeInfoBytes = Base64.getDecoder().decode(encodeInfoHeader.getBytes());
        String userEncodeInfo = new String(encodeInfoBytes);
        encodeInfo = Util.stringToObject(userEncodeInfo, EncodeInfo.class);

        PrivateKey appPrivateKey = this.encryptionService.getAppPrivateKey(encodeInfo);
        String payloadKeyString = CipherUtils.decrypt(EncryptionAlgorithm.RSA, sessionEncryptedKeyHeader, appPrivateKey);
        PayloadKey payloadKey = Util.stringToObject(payloadKeyString, PayloadKey.class);

        SecretKey payloadSecretKey = CipherUtils.getSecretKey(EncryptionAlgorithm.AES_256_CBC_PKCS5_PADDING, payloadKey.getSecret());
        IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(payloadKey.getIv().getBytes()));

        String payload = Util.getPayload(request);

        return CipherUtils.decrypt(EncryptionAlgorithm.AES_256_CBC_PKCS5_PADDING, payload, payloadSecretKey, iv);
    }
}
