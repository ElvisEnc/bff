package bg.com.bo.bff.providers.interfaces;

import bg.com.bo.bff.application.dtos.request.login.LogoutRequest;
import bg.com.bo.bff.providers.dtos.response.jwt.keycloak.CreateTokenServiceResponse;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtAccess;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtKey;
import bg.com.bo.bff.commons.enums.UserRole;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtRefresh;

import java.util.Map;

public interface IJwtProvider {
    CreateTokenServiceResponse generateToken(String personId, String sid, UserRole userRole);

    CreateTokenServiceResponse refreshToken(String refreshToken);

    JwtAccess parseJwtAccess(String token);

    JwtRefresh parseJwtRefresh(String token);

    Map<String, JwtKey> certs();


    boolean revokeAccessToken(String authorization);

    boolean revokeRefreshToken(LogoutRequest request);
}
