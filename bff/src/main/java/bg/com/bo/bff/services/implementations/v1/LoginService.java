package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.login.LoginRequest;
import bg.com.bo.bff.application.dtos.request.login.LogoutRequest;
import bg.com.bo.bff.application.dtos.request.login.RefreshSessionRequest;
import bg.com.bo.bff.application.dtos.response.login.*;
import bg.com.bo.bff.application.dtos.response.generic.GenericResponse;
import bg.com.bo.bff.application.exceptions.GenericException;
import bg.com.bo.bff.commons.enums.config.provider.AppError;
import bg.com.bo.bff.commons.enums.login.LoginSchemaName;
import bg.com.bo.bff.mappings.application.LoginMapper;
import bg.com.bo.bff.mappings.providers.login.ILoginMapper;
import bg.com.bo.bff.providers.dtos.request.login.mw.LoginCredentialMWRequest;
import bg.com.bo.bff.providers.dtos.request.login.mw.LoginFactorMWRequest;
import bg.com.bo.bff.providers.dtos.response.login.mw.LogoutMWResponse;
import bg.com.bo.bff.providers.models.enums.middleware.login.LoginMiddlewareError;
import bg.com.bo.bff.providers.models.enums.middleware.login.LoginMiddlewareResponse;
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
import bg.com.bo.bff.providers.models.middleware.DefaultMiddlewareError;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class LoginService implements ILoginServices {
    private final ILoginMiddlewareProvider provider;
    private final LoginMapper loginMapper;
    private final ILoginMapper mapper;
    private final IJwtProvider jwtService;
    private final LoginServiceMapper loginServiceMapper;

    @Autowired
    public LoginService(ILoginMiddlewareProvider provider, LoginMapper loginMapper, IJwtProvider jwtService, LoginServiceMapper loginServiceMapper, ILoginMapper mapper) {
        this.provider = provider;
        this.loginMapper = loginMapper;
        this.jwtService = jwtService;
        this.loginServiceMapper = loginServiceMapper;
        this.mapper = mapper;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws IOException {
        LoginFactorMWRequest loginMWRequest = LoginFactorMWRequest.builder()
                .codeTypeAuthentication(loginRequest.getType())
                .factor(loginRequest.getUser())
                .build();

        String sid = UUID.randomUUID().toString();
        String factorId = loginRequest.getType();
        LoginSchemaName schema = LoginSchemaName.findByCode(factorId)
                .orElseThrow(() -> new GenericException(LoginMiddlewareError.INVALID_AUTH_TYPE));

        validateCredentials(schema, loginRequest);
        encryptPassword(loginRequest);
        LoginValidationServiceResponse loginValidation = processLoginValidation(schema, loginRequest, loginMWRequest);
        CreateTokenServiceResponse createToken = jwtService.generateToken(loginValidation.getPersonId(), sid, UserRole.LOGGED_USER);
        return handleTokenResponse(createToken, loginValidation);
    }

    private void validateCredentials(LoginSchemaName factorId, LoginRequest loginRequest) {
        EnumSet<LoginSchemaName> passwordRequiredFactors = EnumSet.of(
                LoginSchemaName.PERSON_ID_LOGIN,
                LoginSchemaName.DNI_LOGIN,
                LoginSchemaName.ID_LOGIN
        );
        EnumSet<LoginSchemaName> biometricRequiredFactors = EnumSet.of(
                LoginSchemaName.FINGERPRINT_BIOMETRICS,
                LoginSchemaName.FACIAL_BIOMETRICS
        );

        if (passwordRequiredFactors.contains(factorId) && Util.isStringNullOrEmpty(loginRequest.getPassword())) {
            throw new GenericException(LoginMiddlewareError.PASSWORD_BLANK);
        }

        if (biometricRequiredFactors.contains(factorId) && Util.isStringNullOrEmpty(loginRequest.getTokenBiometric())) {
            throw new GenericException(LoginMiddlewareError.BIOMETRIC_TOKEN);
        }
    }

    private LoginValidationServiceResponse processLoginValidation(LoginSchemaName factorId, LoginRequest loginRequest, LoginFactorMWRequest loginMWRequest) throws IOException {
        LoginValidationServiceResponse loginValidation;
        LoginFactorData data = new LoginFactorData();
        LoginCredentialMWRequest request;

        if (EnumSet.of(LoginSchemaName.FINGERPRINT_BIOMETRICS, LoginSchemaName.FACIAL_BIOMETRICS).contains(factorId)) {
            loginRequest.setPassword(null);
        }

        if (EnumSet.of(LoginSchemaName.PERSON_ID_LOGIN, LoginSchemaName.FINGERPRINT_BIOMETRICS, LoginSchemaName.FACIAL_BIOMETRICS).contains(factorId)) {
            data.setPersonId(loginRequest.getUser());
            request = mapper.mapperRequest(loginRequest, data);
            loginValidation = mapper.convertResponse(data, provider.validateCredentials(request));
        } else {
            LoginFactorMWResponse loginMWFactorResponse = provider.validateFactorUser(loginMWRequest);
            request = mapper.mapperRequest(loginRequest, loginMWFactorResponse.getData());
            loginValidation = mapper.convertResponse(loginMWFactorResponse.getData(), provider.validateCredentials(request));
        }

        return loginValidation;
    }

    private LoginResponse handleTokenResponse(CreateTokenServiceResponse createToken, LoginValidationServiceResponse loginValidation) {
        return switch (createToken.getStatusCode()) {
            case SUCCESS -> {
                LoginResult loginResult = loginServiceMapper.convert(createToken, loginValidation, LoginResult.StatusCode.SUCCESS);
                yield loginMapper.convert(loginResult);
            }
            case INVALID_DATA ->
                    throw new GenericException(String.format("Estado no valido para Login. %s", createToken.getStatusCode()),
                            AppError.BAD_REQUEST.getHttpCode(),
                            AppError.BAD_REQUEST.getCode(),
                            "Datos inválidos");
            default -> throw new GenericException("Unexpected error during login");
        };
    }

    private void encryptPassword(LoginRequest loginRequest) {
        if (!loginRequest.getPassword().isBlank()) {
            String encrypted = Util.encodeSha512(loginRequest.getPassword());
            loginRequest.setPassword(encrypted);
        }
    }

    @Override
    public TokenDataResponse refreshSession(String personId, RefreshSessionRequest refreshSessionRequest, String accessJwt) {
        JwtRefresh jwtRefresh = jwtService.parseJwtRefresh(refreshSessionRequest.getRefreshToken());

        if (!jwtRefresh.getPayload().getPersonId().trim().equals(personId.trim()))
            throw new GenericException(DefaultMiddlewareError.INVALID_ACCESS_JWT);

        String accessToken = accessJwt.substring(7);

        if (!jwtService.revokeAccessToken(accessToken))
            throw new GenericException(DefaultMiddlewareError.BLACKLISTED_ACCESS_JWT);

        CreateTokenServiceResponse createTokenResponse = jwtService.refreshToken(refreshSessionRequest.getRefreshToken());
        return switch (createTokenResponse.getStatusCode()) {
            case SUCCESS -> {
                RefreshSessionResult refreshSessionResult = loginServiceMapper.convert(createTokenResponse.getTokenData(), RefreshSessionResult.StatusCode.SUCCESS);
                yield loginServiceMapper.convert(refreshSessionResult);
            }
            case INVALID_DATA -> throw new GenericException(DefaultMiddlewareError.INVALID_ACCESS_JWT);
            default -> throw new GenericException(DefaultMiddlewareError.AUTHENTICATION_FILTER_FAILURE);
        };
    }

    @Override
    public GenericResponse logout(String personId, String authorization, String personRoleId, LogoutRequest request) throws IOException {
        String accessToken = authorization.substring(7);
        validateRequestToken(request.getRefreshToken());
        if (!jwtService.revokeAccessToken(accessToken) || !jwtService.revokeRefreshToken(request.getRefreshToken())) {
            AppError errorKetCloak = AppError.KEY_CLOAK_ERROR;
            throw new GenericException(errorKetCloak.getMessage(), errorKetCloak.getHttpCode(), errorKetCloak.getCode());
        }

        LogoutMWRequest logoutMWRequest = mapper.mapperRequest(personId, personRoleId);
        LogoutMWResponse mwResponse = provider.logout(logoutMWRequest);
        if (mwResponse.getData().getId() != null)
            return GenericResponse.instance(LoginMiddlewareResponse.SUCCESS_LOGOUT);
        else throw new GenericException(DefaultMiddlewareError.INTERNAL_SERVER_ERROR);
    }

    private void validateRequestToken(String token) {
        AppError errorKetCloak = AppError.KEY_CLOAK_ERROR;
        IntrospectTokenKCResponse introspect = jwtService.introspect(token);
        if (introspect.getResult() != IntrospectTokenKCResponse.Result.SUCCESS) {
            if (Arrays.asList(IntrospectTokenKCResponse.Result.EXPIRED_TOKEN,
                    IntrospectTokenKCResponse.Result.INVALID_TOKEN).contains(introspect.getResult())) {
                throw new GenericException(DefaultMiddlewareError.INVALID_ACCESS_JWT);
            } else
                throw new GenericException(errorKetCloak.getMessage(), errorKetCloak.getHttpCode(), errorKetCloak.getCode());
        }
    }

    @Override
    public DeviceEnrollmentResponse validation() throws IOException {
        DeviceEnrollmentMWResponse deviceEnrollmentResponse = provider.makeValidateDevice();
        return mapper.convertResponse(deviceEnrollmentResponse);
    }
}
