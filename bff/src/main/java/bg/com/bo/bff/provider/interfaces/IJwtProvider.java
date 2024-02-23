package bg.com.bo.bff.provider.interfaces;

import bg.com.bo.bff.model.dtos.login.CreateTokenServiceResponse;
import bg.com.bo.bff.model.jwt.JwtAccess;
import bg.com.bo.bff.model.jwt.JwtKey;
import bg.com.bo.bff.model.enums.UserRole;

import java.util.Map;

public interface IJwtProvider {
    CreateTokenServiceResponse generateToken(String personId, UserRole userStatus);

    CreateTokenServiceResponse refreshToken(String refreshToken);

    JwtAccess parseJwtAccess(String token);

    Map<String, JwtKey> certs();
}
