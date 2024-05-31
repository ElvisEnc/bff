package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.LoginRequest;
import bg.com.bo.bff.application.dtos.request.LogoutRequest;
import bg.com.bo.bff.application.dtos.request.RefreshSessionRequest;
import bg.com.bo.bff.application.dtos.response.DeviceEnrollmentResponse;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.dtos.response.TokenDataResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.AppError;
import bg.com.bo.bff.commons.enums.DeviceMW;
import bg.com.bo.bff.commons.enums.LoginSchemaName;
import bg.com.bo.bff.commons.enums.response.GenericControllerErrorResponse;
import bg.com.bo.bff.commons.enums.response.RefreshControllerErrorResponse;
import bg.com.bo.bff.commons.utils.Util;
import bg.com.bo.bff.mappings.services.LoginServiceMapper;
import bg.com.bo.bff.models.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.models.dtos.login.*;
import bg.com.bo.bff.commons.enums.UserRole;
import bg.com.bo.bff.application.exceptions.NotHandledResponseException;
import bg.com.bo.bff.application.exceptions.NotValidStateException;
import bg.com.bo.bff.models.jwt.JwtRefresh;
import bg.com.bo.bff.providers.dtos.request.login.LogoutMWRequest;
import bg.com.bo.bff.providers.dtos.response.login.DeviceEnrollmentMWResponse;
import bg.com.bo.bff.providers.dtos.response.login.LoginFactorData;
import bg.com.bo.bff.providers.dtos.response.login.LoginFactorMWResponse;
import bg.com.bo.bff.providers.interfaces.IJwtProvider;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
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
    public LoginResult login(LoginRequest loginRequest, Map<String, String> parameters) throws IOException {
        String factorId = loginRequest.getType();
        String loginPerson = LoginSchemaName.PERSON_ID_LOGIN.getCode();
        String loginFinger = LoginSchemaName.FINGERPRINT_BIOMETRICS.getCode();
        String loginFacial = LoginSchemaName.FACIAL_BIOMETRICS.getCode();

        if (Objects.equals(factorId, loginFinger) || Objects.equals(factorId, loginFacial)) {
            if (loginRequest.getTokenBiometric() == null || loginRequest.getTokenBiometric().isEmpty()) {
                throw new GenericException("Token Biometrico no debe estar vacío", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
            }
        } else if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new GenericException("Password no debe estar vacío", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
        }

        String jsonDataName = DeviceMW.JSON_DATA.getCode();
        if (parameters.containsKey(jsonDataName)) {
            String data = Util.decodeBase64ToString(parameters.get(jsonDataName));
            parameters.put(jsonDataName, data);
        } else
            throw new GenericException("El header json-data es requerido", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());

        String encrypted = Util.encodeSha512(loginRequest.getPassword());
        loginRequest.setPassword(encrypted);
        LoginValidationServiceResponse loginValidation;
        if (Objects.equals(factorId, loginPerson) || Objects.equals(factorId, loginFinger) || Objects.equals(factorId, loginFacial)) {
            String user = loginRequest.getUser();
            if (!user.matches("\\d+"))
                throw new GenericException("Se espera el Código de Persona en user", AppError.BAD_REQUEST.getHttpCode(), AppError.BAD_REQUEST.getCode());
            LoginFactorData data = new LoginFactorData();
            data.setPersonId(user);
            loginValidation = loginMiddlewareService.validateCredentials(loginRequest, data, parameters);
        } else {
            LoginFactorMWResponse loginMWFactorResponse = loginMiddlewareService.validateFactorUser(loginRequest, parameters);
            loginValidation = loginMiddlewareService.validateCredentials(loginRequest, loginMWFactorResponse.getData(), parameters);
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

        return loginMiddlewareService.logout(deviceId, deviceIp, deviceName, geoPositionX, geoPositionY, appVersion, logoutMWRequest);
    }

    public DeviceEnrollmentResponse validation(Map<String, String> parameter) throws IOException {


        DeviceEnrollmentMWResponse deviceEnrollmentResponse = loginMiddlewareService.makeValidateDevice( parameter);

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
