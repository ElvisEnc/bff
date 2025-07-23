package bg.com.bo.bff.providers.dtos.request.login.agm;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class UserKeysRequest {
    private String userPublicKey;
    private String appPublicKey;
    private String appPrivateKey;
}
