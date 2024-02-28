package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.models.dtos.login.CreateTokenServiceResponse;
import bg.com.bo.bff.models.jwt.JwtAccess;
import bg.com.bo.bff.models.jwt.JwtKey;
import bg.com.bo.bff.commons.enums.UserRole;

import java.util.Map;

public interface IJwtProvider {
    CreateTokenServiceResponse generateToken(String personId, UserRole userStatus);

    CreateTokenServiceResponse refreshToken(String refreshToken);

    JwtAccess parseJwtAccess(String token);

    Map<String, JwtKey> certs();
}
