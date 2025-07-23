package bg.com.bo.bff.models.payload.encryption;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class PayloadKey {
    private String secret;
    private String iv;
}
