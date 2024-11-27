package bg.com.bo.bff.models.payload.encryption;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnonymousUserEncryptInfo {
    @JsonProperty("public")
    private String publicUserKey;
    @JsonProperty("id")
    private String serverKeyId;
}
