package bg.com.bo.bff.providers.models.enums.keycloak;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyCloakService {
    CERTS("/certs"),
    REVOKE_TOKEN("/revoke"),
    CREATE_TOKEN("/token"),
    INTROSPECT("/token/introspect");
    private final String serviceUrl;
}
