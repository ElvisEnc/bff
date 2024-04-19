package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.request.LogoutRequest;
import bg.com.bo.bff.application.dtos.request.RefreshSessionRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.TokenDataResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.LoginSchemaName;
import bg.com.bo.bff.commons.enums.response.GenericControllerErrorResponse;
import bg.com.bo.bff.commons.enums.response.RefreshControllerErrorResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.mappings.services.LoginServiceMapper;
import bg.com.bo.bff.models.ClientToken;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.models.dtos.login.*;
import bg.com.bo.bff.commons.enums.UserRole;
import bg.com.bo.bff.application.exceptions.NotHandledResponseException;
import bg.com.bo.bff.application.exceptions.NotValidStateException;
import bg.com.bo.bff.models.jwt.JwtRefresh;
import bg.com.bo.bff.providers.dtos.requests.login.LogoutMWRequest;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorDataResponse;
import bg.com.bo.bff.providers.dtos.responses.login.LoginMWFactorResponse;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class LoginService implements ILoginServices {

    private ILoginMiddlewareProvider loginMiddlewareService;
    private IJwtProvider jwtService;
    private LoginServiceMapper loginServiceMapper;

    @Autowired
    public LoginService(ILoginMiddlewareProvider loginMiddlewareService, IJwtProvider jwtService, LoginServiceMapper loginMapper) {
        this.loginMiddlewareService = loginMiddlewareService;
        this.jwtService = jwtService;
        this.loginServiceMapper = loginMapper;
    }

    @Override
    public LoginResult login(LoginRequest loginRequest, String ip) throws IOException {
        String encrypted = Util.encodeSha512(loginRequest.getPassword());
        loginRequest.setPassword(encrypted);

        ClientToken clientToken = loginMiddlewareService.tokenLogin();
        String token = clientToken.getAccessToken();
        String factorId = loginRequest.getType();
        LoginValidationServiceResponse loginValidation;
        if (Objects.equals(factorId, LoginSchemaName.PERSONIDLOGIN.getCode())) {
            LoginMWFactorDataResponse loginMWFactorDataResponse = new LoginMWFactorDataResponse();
            loginMWFactorDataResponse.setPersonId(loginRequest.getUser());
            loginMWFactorDataResponse.setSecondFactor("1");
            loginValidation = loginMiddlewareService.validateCredentials(loginRequest, ip, token, loginMWFactorDataResponse);
        } else {
            LoginMWFactorResponse loginMWFactorResponse = loginMiddlewareService.validateFactorUser(loginRequest, ip, token);
            loginValidation = loginMiddlewareService.validateCredentials(loginRequest, ip, token, loginMWFactorResponse.getData());
        }

        CreateTokenServiceResponse createToken = jwtService.generateToken(loginValidation.getPersonId(), UserRole.LOGGED_USER);

        switch (createToken.getStatusCode()) {
            case SUCCESS:
                return loginServiceMapper.convert(createToken, loginValidation, LoginResult.StatusCode.SUCCESS);
            case INVALID_DATA:
                throw new NotValidStateException(String.format("Estado no valido para Login. %s", createToken.getStatusCode()));
            default:
                throw new NotHandledResponseException();
        }
    }

    @Override
    public TokenDataResponse refreshSession(String personId, RefreshSessionRequest refreshSessionRequest) {
        JwtRefresh jwtRefresh = jwtService.parseJwtRefresh(refreshSessionRequest.getRefreshToken());

        if (!jwtRefresh.getPayload().getPersonId().trim().equals(personId.trim()))
            throw new HandledException(RefreshControllerErrorResponse.INVALID_DATA);

        try {
            CreateTokenServiceResponse createTokenResponse = jwtService.refreshToken(refreshSessionRequest.getRefreshToken());

            switch (createTokenResponse.getStatusCode()) {
                case SUCCESS:
                    RefreshSessionResult refreshSessionResult = loginServiceMapper.convert(createTokenResponse.getTokenData(), RefreshSessionResult.StatusCode.SUCCESS);
                    return loginServiceMapper.convert(refreshSessionResult);
                case INVALID_DATA:
                    throw new HandledException(RefreshControllerErrorResponse.INVALID_DATA);
                default:
                    throw new NotHandledResponseException();
            }
        } catch (HandledException | NotHandledResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public GenericResponse logout(String deviceId, String deviceIp, String deviceName, String geoPositionX, String geoPositionY, String appVersion, String personId, String userDeviceId, String personRoleId, String authorization,
                                  LogoutRequest request) throws IOException {

        String accessToken = authorization.substring(7);
        if (!jwtService.revokeAccessToken(accessToken) || !jwtService.revokeRefreshToken(request)) {
            AppError errorKetCloak = AppError.KEY_CLOAK_ERROR;
            throw new GenericException(errorKetCloak.getMessage(), errorKetCloak.getHttpCode(), errorKetCloak.getCode());
        }

        LogoutMWRequest logoutMWRequest = LogoutMWRequest.builder()
                .ownerAccount(LogoutMWRequest.OwnerAccount.builder()
                        .personId(personId)
                        .userDeviceId(userDeviceId)
                        .personRoleId(personRoleId)
                        .build())
                .deviceData(LogoutMWRequest.LogoutDataMW.builder()
                        .deviceId(deviceId)
                        .deviceIp(deviceIp)
                        .deviceName(deviceName)
                        .geoPositionX(geoPositionX)
                        .geoPositionY(geoPositionY)
                        .appVersion(appVersion)
                        .build())
                .build();

        ClientToken clientToken = loginMiddlewareService.tokenLogin();
        return loginMiddlewareService.logout(deviceId, deviceIp, deviceName, geoPositionX, geoPositionY, appVersion, personId, userDeviceId, personRoleId, logoutMWRequest, clientToken.getAccessToken()
        );
    }
}
