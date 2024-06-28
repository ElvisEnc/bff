package bg.com.bo.bff.mappings.services.auth;

import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.models.jwt.JwtAccess;

public interface IAuthMapper {
    UserData convert(JwtAccess jwtAccess);
}
