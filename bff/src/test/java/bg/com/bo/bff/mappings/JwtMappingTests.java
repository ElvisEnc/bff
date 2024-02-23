package bg.com.bo.bff.mappings;

import bg.com.bo.bff.mappings.services.keycloak.KeyCloakJsonMapper;
import bg.com.bo.bff.model.jwt.JwtHeader;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtMappingTests {
    @Test
    void givenJwtHeaderAsJsonWhenMappingThenReturnJwtHeaderObject() throws IOException {
        //Arrange
        String json = IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/files/JwtHeader.json")), StandardCharsets.UTF_8);

        //Act
        KeyCloakJsonMapper keyCloakJsonMapper = new KeyCloakJsonMapper();
        JwtHeader header = keyCloakJsonMapper.convertToJwtHeader(json);

        //Assert
        assertNotNull(header.getId());
    }
}
