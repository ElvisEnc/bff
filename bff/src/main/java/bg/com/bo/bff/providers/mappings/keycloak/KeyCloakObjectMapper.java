package bg.com.bo.bff.providers.mappings.keycloak;

import bg.com.bo.bff.models.dtos.TokenDataServiceResponse;
import bg.com.bo.bff.models.jwt.JwtKey;
import bg.com.bo.bff.providers.dtos.responses.keycloak.CreateTokenKCResponse;
import bg.com.bo.bff.providers.dtos.responses.keycloak.KeyCloakKeyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface KeyCloakObjectMapper {
    KeyCloakObjectMapper INSTANCE = Mappers.getMapper(KeyCloakObjectMapper.class);

    TokenDataServiceResponse convert(CreateTokenKCResponse createTokenKCResponse);

    JwtKey convert(KeyCloakKeyResponse keyCloakKeyResponse);
}
