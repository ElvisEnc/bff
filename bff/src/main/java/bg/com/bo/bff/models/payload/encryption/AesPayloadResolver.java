package bg.com.bo.bff.models.payload.encryption;

import bg.com.bo.bff.models.encryptor.EncryptorFactory;
import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import bg.com.bo.bff.commons.utils.CipherUtils;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.models.encryptor.IEncryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AesPayloadResolver {

    private final EncryptionAlgorithm encryptionAlgorithm;
    private final IEncryptor encryptor;

    private AesPayloadResolver(String payloadAlgorithm) {
        this.encryptionAlgorithm = EncryptionAlgorithm.valueOf(payloadAlgorithm);
        this.encryptor = EncryptorFactory.instance(payloadAlgorithm);
    }

    public static AesPayloadResolver instance(String payloadAlgorithm) {
        return new AesPayloadResolver(payloadAlgorithm);
    }

    public EncryptionPayloadResult encrypt(String body, String contentType) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        SecretKey secret = CipherUtils.generateKey(encryptionAlgorithm);
        IvParameterSpec iv = CipherUtils.generateIv();
        String encryptedBody = encryptor.encrypt(encryptionAlgorithm, body, secret, iv);

        return EncryptionPayloadResult.builder()
                .data(EncryptionPayload.builder()
                        .body(encryptedBody)
                        .contentType(contentType)
                        .build())
                .secretKey(secret)
                .iv(iv)
                .build();
    }

    public EncryptionPayload decrypt(String keysData, EncryptionPayload encryptedPayload) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        PayloadKey payloadKey = Util.stringToObject(keysData, PayloadKey.class);
        SecretKey payloadSecretKey = CipherUtils.getSecretKey(encryptionAlgorithm, payloadKey.getSecret());
        IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(Util.getEncodedBytes(payloadKey.getIv())));

        String decryptedPayload = "";
        String contentType = null;

        if (encryptedPayload != null) {
            decryptedPayload = encryptedPayload.getBody();
            if (decryptedPayload != null && !decryptedPayload.isEmpty())
                decryptedPayload = encryptor.decrypt(this.encryptionAlgorithm, encryptedPayload.getBody(), payloadSecretKey, iv);

            contentType = encryptedPayload.getContentType();
        }

        EncryptionPayload encryptionPayloadDecrypted = EncryptionPayload.builder()
                .body(decryptedPayload)
                .build();

        if (contentType != null)
            encryptionPayloadDecrypted.setContentType(contentType);

        return encryptionPayloadDecrypted;
    }
}
