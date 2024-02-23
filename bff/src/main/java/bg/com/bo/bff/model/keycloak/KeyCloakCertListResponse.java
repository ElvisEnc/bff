package bg.com.bo.bff.model.keycloak;

import lombok.Singular;

import java.io.Serializable;
import java.util.List;

@lombok.Data
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class KeyCloakCertListResponse implements Serializable {
    @Singular("key")
    private List<KeyCloakKeyResponse> keys;
}
