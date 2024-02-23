package bg.com.bo.bff.mappings.services.keycloak;

import bg.com.bo.bff.model.dtos.TokenDataServiceResponse;
import bg.com.bo.bff.model.jwt.JwtKey;
import bg.com.bo.bff.model.dtos.keycloak.CreateTokenKCResponse;
import bg.com.bo.bff.model.keycloak.KeyCloakKeyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface KeyCloakObjectMapper {
    KeyCloakObjectMapper INSTANCE = Mappers.getMapper(KeyCloakObjectMapper.class);

    TokenDataServiceResponse convert(CreateTokenKCResponse createTokenKCResponse);

    JwtKey convert(KeyCloakKeyResponse keyCloakKeyResponse);
}
