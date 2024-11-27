package bg.com.bo.bff.models.payload.encryption;

import bg.com.bo.bff.commons.utils.CipherUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.KeyPair;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EncryptionKey {
    private String id;
    private String publicKey;
    private String privateKey;

    public static EncryptionKey create(KeyPair keyPair) {
        return new EncryptionKey(
                UUID.randomUUID().toString(),
                CipherUtils.encodeKeyToBase64(keyPair.getPublic()),
                CipherUtils.encodeKeyToBase64(keyPair.getPrivate()));
    }
}
