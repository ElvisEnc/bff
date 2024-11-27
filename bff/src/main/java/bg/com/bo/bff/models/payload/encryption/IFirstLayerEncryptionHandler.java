package bg.com.bo.bff.models.payload.encryption;

public interface IFirstLayerEncryptionHandler {
    String decrypt();

    String encrypt(PayloadKey payloadKey);
}
