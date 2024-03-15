package bg.com.bo.bff.models;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class PayloadKey {
    private String secret;
    private String iv;
}
