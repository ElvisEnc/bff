package bg.com.bo.bff.providers.dtos.requests.loginagn;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class UserKeysRequest {
    private String userPublicKey;
    private String appPublicKey;
    private String appPrivateKey;
}
