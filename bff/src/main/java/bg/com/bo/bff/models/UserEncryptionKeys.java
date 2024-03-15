package bg.com.bo.bff.models;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class UserEncryptionKeys {
    private String appPublicKey;
    private String appPrivateKey;
    private String userPublicKey;
}
