package bg.com.bo.bff.mappings.services.keycloak;

import bg.com.bo.bff.providers.dtos.response.jwt.JwtHeader;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;

public interface IKeyCloakJsonMapper {
    JwtHeader convertJwtHeader(String json) throws JsonProcessingException;

    JwtPayload convertJwtPayload(Claims claims) throws JsonProcessingException;
}
