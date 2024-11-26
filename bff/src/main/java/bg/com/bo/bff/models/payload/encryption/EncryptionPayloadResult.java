package bg.com.bo.bff.models.payload.encryption;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

@lombok.Data
@lombok.Builder
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class EncryptionPayloadResult {
    private EncryptionPayload data;
    private SecretKey secretKey;
    private IvParameterSpec iv;
}
