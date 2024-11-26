package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.login.LogoutRequest;
import bg.com.bo.bff.application.dtos.request.login.RefreshSessionRequest;
import bg.com.bo.bff.application.dtos.response.login.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.enums.config.provider.DeviceMW;
import bg.com.bo.bff.commons.enums.login.LoginSchemaName;
import bg.com.bo.bff.providers.models.enums.middleware.response.GenericControllerErrorResponse;
import bg.com.bo.bff.providers.models.enums.middleware.response.RefreshControllerErrorResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.mappings.services.LoginServiceMapper;
import bg.com.bo.bff.commons.enums.user.UserRole;
import bg.com.bo.bff.providers.dtos.response.jwt.JwtRefresh;
import bg.com.bo.bff.providers.dtos.request.login.mw.LogoutMWRequest;
import bg.com.bo.bff.providers.dtos.response.jwt.keycloak.CreateTokenServiceResponse;
import bg.com.bo.bff.providers.dtos.response.keycloak.IntrospectTokenKCResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.DeviceEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginFactorData;
import bg.com.bo.bff.providers.dtos.response.login.mw.LoginFactorMWResponse;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class LoginService implements ILoginServices {
    private final ILoginMiddlewareProvider provider;
    private final IJwtProvider jwtService;
    private final LoginServiceMapper loginServiceMapper;

    @Autowired
    public LoginService(ILoginMiddlewareProvider provider, IJwtProvider jwtService, LoginServiceMapper loginMapper) {
        this.provider = provider;
        this.jwtService = jwtService;
        this.loginServiceMapper = loginMapper;
    }

    @Override
    public LoginResult login(LoginRequest loginRequest, Map<String, String> parameters) throws IOException {
        String sid = UUID.randomUUID().toString();

        String factorId = loginRequest.getType();
        String loginPerson = LoginSchemaName.PERSON_ID_LOGIN.getCode();
        String loginFinger = LoginSchemaName.FINGERPRINT_BIOMETRICS.getCode();
        String loginFacial = LoginSchemaName.FACIAL_BIOMETRICS.getCode();

        if (Objects.equals(factorId, loginFinger) || Objects.equals(factorId, loginFacial)) {
            if (Util.isStringNullOrEmpty(loginRequest.getTokenBiometric())) {
                throw new GenericException("Token Biometrico no debe estar vacío", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
            }
        } else if (Util.isStringNullOrEmpty(loginRequest.getPassword())) {
            throw new GenericException("Password no debe estar vacío", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
        }
        decodeJsonData(parameters);
        encryptPassword(loginRequest);
        LoginValidationServiceResponse loginValidation;
        String user = loginRequest.getUser();
        if (Objects.equals(factorId, loginPerson) || Objects.equals(factorId, loginFinger) || Objects.equals(factorId, loginFacial)) {
            validateNumber(user);
            LoginFactorData data = new LoginFactorData();
            data.setPersonId(user);
            loginValidation = provider.validateCredentials(loginRequest, data, parameters);
        } else {
            if (Objects.equals(factorId, LoginSchemaName.DNI_LOGIN.getCode()))
                validateNumber(user);
            LoginFactorMWResponse loginMWFactorResponse = provider.validateFactorUser(loginRequest, parameters);
            loginValidation = provider.validateCredentials(loginRequest, loginMWFactorResponse.getData(), parameters);
        }

        CreateTokenServiceResponse createToken = jwtService.generateToken(loginValidation.getPersonId(), sid, UserRole.LOGGED_USER);

        return switch (createToken.getStatusCode()) {
            case SUCCESS -> loginServiceMapper.convert(createToken, loginValidation, LoginResult.StatusCode.SUCCESS);
            case INVALID_DATA ->
                    throw new GenericException(String.format("Estado no valido para Login. %s", createToken.getStatusCode()), AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
            default -> throw new GenericException();
        };
    }

    private void decodeJsonData(Map<String, String> parameters) {
        String jsonDataName = DeviceMW.JSON_DATA.getCode();
        if (!parameters.containsKey(jsonDataName)) {
            throw new GenericException("El header json-data es requerido", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
        }
        String data = Util.decodeBase64ToString(parameters.get(jsonDataName));
        parameters.put(jsonDataName, data);
    }

    private void encryptPassword(LoginRequest loginRequest) {
        String encrypted = Util.encodeSha512(loginRequest.getPassword());
        loginRequest.setPassword(encrypted);
    }

    private void validateNumber(String user) {
        if (!user.matches("\\d+")) {
            throw new GenericException("Se espera solo números en User", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
        }
    }

    @Override
    public TokenDataResponse refreshSession(String personId, RefreshSessionRequest refreshSessionRequest, String accessJwt) {
        JwtRefresh jwtRefresh = jwtService.parseJwtRefresh(refreshSessionRequest.getRefreshToken());

        if (!jwtRefresh.getPayload().getPersonId().trim().equals(personId.trim()))
            throw new HandledException(RefreshControllerErrorResponse.INVALID_DATA);

        String accessToken = accessJwt.substring(7);
        if(!jwtService.revokeAccessToken(accessToken))
            throw new HandledException(RefreshControllerErrorResponse.INVALID_DATA);

        try {
            CreateTokenServiceResponse createTokenResponse = jwtService.refreshToken(refreshSessionRequest.getRefreshToken());

            return switch (createTokenResponse.getStatusCode()) {
                case SUCCESS -> {
                    RefreshSessionResult refreshSessionResult = loginServiceMapper.convert(createTokenResponse.getTokenData(), RefreshSessionResult.StatusCode.SUCCESS);
                    yield loginServiceMapper.convert(refreshSessionResult);
                }
                case INVALID_DATA -> throw new HandledException(RefreshControllerErrorResponse.INVALID_DATA);
                default -> throw new GenericException();
            };
        } catch (HandledException e) {
            throw e;
        } catch (Exception e) {
            throw new HandledException(GenericControllerErrorResponse.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Override
    public GenericResponse logout(String deviceId,
                                  String deviceIp,
                                  String deviceName,
                                  String geoPositionX,
                                  String geoPositionY,
                                  String appVersion,
                                  String personId,
                                  String userDeviceId,
                                  String personRoleId,
                                  String authorization,
                                  LogoutRequest request) throws IOException {

        String accessToken = authorization.substring(7);

        validateRequestToken(request.getRefreshToken());

        if (!jwtService.revokeAccessToken(accessToken) || !jwtService.revokeRefreshToken(request.getRefreshToken())) {
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

        return provider.logout(deviceId, deviceIp, deviceName, geoPositionX, geoPositionY, appVersion, logoutMWRequest);
    }

    private void validateRequestToken(String token) {
        AppError errorKetCloak = AppError.KEY_CLOAK_ERROR;
        IntrospectTokenKCResponse introspect = jwtService.introspect(token);
        if (introspect.getResult() != IntrospectTokenKCResponse.Result.SUCCESS) {
            if (Arrays.asList(IntrospectTokenKCResponse.Result.EXPIRED_TOKEN,
                    IntrospectTokenKCResponse.Result.INVALID_TOKEN).contains(introspect.getResult())) {
                AppError invalidRefreshTokenError = AppError.INVALID_REFRESH_TOKEN;
                throw new GenericException(invalidRefreshTokenError.getMessage(), invalidRefreshTokenError.getHttpCode(), invalidRefreshTokenError.getCode());
            } else
                throw new GenericException(errorKetCloak.getMessage(), errorKetCloak.getHttpCode(), errorKetCloak.getCode());
        }
    }

    @Override
    public DeviceEnrollmentResponse validation(Map<String, String> parameter) throws IOException {
        DeviceEnrollmentMWResponse deviceEnrollmentResponse = provider.makeValidateDevice(parameter);

        String enrolled = deviceEnrollmentResponse.getStatusCode();
        DeviceEnrollmentResponse response = new DeviceEnrollmentResponse();
        if (Objects.equals(enrolled, "ENROLLED")) {
            response.setPersonId(deviceEnrollmentResponse.getPersonId());
            response.setStatusCode(1);
        } else
            response.setStatusCode(2);
        return response;
    }
}
