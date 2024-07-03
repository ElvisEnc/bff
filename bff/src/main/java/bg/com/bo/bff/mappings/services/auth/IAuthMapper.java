package bg.com.bo.bff.mappings.services.auth;

import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtAccess;

public interface IAuthMapper {
    UserData convert(JwtAccess jwtAccess);
}
