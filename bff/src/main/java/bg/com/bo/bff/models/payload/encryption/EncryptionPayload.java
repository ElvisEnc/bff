package bg.com.bo.bff.models.payload.encryption;

@lombok.Data
@lombok.Builder
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class EncryptionPayload {
    private String body;
    private String contentType;
}
