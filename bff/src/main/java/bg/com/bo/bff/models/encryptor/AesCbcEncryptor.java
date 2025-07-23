package bg.com.bo.bff.models.encryptor;

import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;
import bg.com.bo.bff.commons.utils.Util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AesCbcEncryptor implements IEncryptor {

    private AesCbcEncryptor() {
    }

    public static AesCbcEncryptor instance() {
        return new AesCbcEncryptor();
    }

    @Override
    public String decrypt(EncryptionAlgorithm encryptionAlgorithm, String data, SecretKey secretKey, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm.getCode());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] dataDecoded = Base64.getDecoder().decode(data);
        byte[] dataDecrypted = cipher.doFinal(dataDecoded);
        return Util.getStringFromEncodedBytes(dataDecrypted);
    }

    @Override
    public String encrypt(EncryptionAlgorithm encryptionAlgorithm, String data, SecretKey secretKey, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm.getCode());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        byte[] dataEncrypted = cipher.doFinal(Util.getEncodedBytes(data));
        return Base64.getEncoder().encodeToString(dataEncrypted);
    }
}
