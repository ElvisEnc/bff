package bg.com.bo.bff.commons.utils;

import bg.com.bo.bff.commons.enums.config.provider.EncryptionAlgorithm;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CipherUtils {
    public static String decrypt(EncryptionAlgorithm encryptionAlgorithm, String data, PrivateKey appPrivateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm.getCode());
        cipher.init(Cipher.DECRYPT_MODE, appPrivateKey);
        byte[] dataDecoded = Base64.getDecoder().decode(data);
        byte[] decryptedData = cipher.doFinal(dataDecoded);
        return Util.getStringFromEncodedBytes(decryptedData);
    }

    public static PublicKey createPublicKey(EncryptionAlgorithm alg, String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encodedPublicKey = Base64.getDecoder().decode(publicKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedPublicKey);

        KeyFactory fact = KeyFactory.getInstance(alg.getCode());

        return fact.generatePublic(keySpec);
    }

    public static String encrypt(EncryptionAlgorithm encryptionAlgorithm, String data, PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm.getCode());
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] dataEncrypted = cipher.doFinal(Util.getEncodedBytes(data));
        return Base64.getEncoder().encodeToString(dataEncrypted);
    }

    public static PrivateKey createPrivateKey(EncryptionAlgorithm alg, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encodedPublicKey = Base64.getDecoder().decode(privateKey);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPublicKey);

        KeyFactory fact = KeyFactory.getInstance(alg.getCode());

        return fact.generatePrivate(keySpec);
    }

    public static SecretKey getSecretKey(EncryptionAlgorithm alg, String secretKey) {
        byte[] keyBytes = Base64.getDecoder().decode(Util.getEncodedBytes(secretKey));
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, alg.getFamily());
    }

    public static SecretKey generateKey(EncryptionAlgorithm encryptionAlgorithm) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(encryptionAlgorithm.getFamily());
        keyGenerator.init(encryptionAlgorithm.getKeySize());
        return keyGenerator.generateKey();
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encodeKeyToBase64(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
