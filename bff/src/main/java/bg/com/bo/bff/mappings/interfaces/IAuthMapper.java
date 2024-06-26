package bg.com.bo.bff.mappings.interfaces;

import bg.com.bo.bff.models.UserData;
import bg.com.bo.bff.models.jwt.JwtAccess;

public interface IAuthMapper {
    UserData convert(JwtAccess jwtAccess);
}
