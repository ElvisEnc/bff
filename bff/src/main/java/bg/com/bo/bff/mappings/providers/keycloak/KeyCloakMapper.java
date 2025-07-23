package bg.com.bo.bff.mappings.providers.keycloak;

import bg.com.bo.bff.mappings.services.keycloak.KeyCloakJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@lombok.Getter
public class KeyCloakMapper {
    private KeyCloakObjectMapper objectMapper;
    private KeyCloakJsonMapper jsonMapper;

    @Autowired
    public KeyCloakMapper(KeyCloakObjectMapper keyCloakObjectMapper, KeyCloakJsonMapper keyCloakJsonMapper) {
        this.objectMapper = keyCloakObjectMapper;
        this.jsonMapper = keyCloakJsonMapper;
    }
}