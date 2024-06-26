package bg.com.bo.bff.mappings.interfaces;

import bg.com.bo.bff.models.jwt.JwtHeader;
import bg.com.bo.bff.models.jwt.JwtPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;

public interface IKeyCloakJsonMapper {
    JwtHeader convertJwtHeader(String json) throws JsonProcessingException;

    JwtPayload convertJwtPayload(Claims claims) throws JsonProcessingException;
}
