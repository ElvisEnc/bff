package bg.com.bo.bff.services.v1;

import bg.com.bo.bff.controllers.request.LoginRequest;
import bg.com.bo.bff.controllers.request.RefreshSessionRequest;
import bg.com.bo.bff.mappings.services.LoginServiceMapper;
import bg.com.bo.bff.model.dtos.middleware.ClientMWToken;
import bg.com.bo.bff.model.dtos.login.LoginValidationServiceResponse;
import bg.com.bo.bff.model.dtos.login.*;
import bg.com.bo.bff.model.enums.UserRole;
import bg.com.bo.bff.model.exceptions.NotHandledResponseException;
import bg.com.bo.bff.model.exceptions.NotValidStateException;
import bg.com.bo.bff.provider.interfaces.IJwtProvider;
import bg.com.bo.bff.provider.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.services.interfaces.ILoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginServices {
    @Value("${middleware}")
    private String baseUrl;

    @Value("${login.oauth.middleware}")
    private String complementToken;

    @Value("${client.secret.login.api}")
    private String clientSecret;

    @Value("${login.url.complement}")
    private String urlComplement;

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
    public LoginResult login(LoginRequest loginRequest) {
        ClientMWToken clientToken = loginMiddlewareService.generateAccessToken();
        String token = clientToken.getAccessToken();
        LoginValidationServiceResponse loginValidation = loginMiddlewareService.validateCredentials(token, loginRequest);

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
    public RefreshSessionResult refreshSession(String personId, RefreshSessionRequest refreshSessionRequest) {
        //TODO: validacion de estado de usuario.
        CreateTokenServiceResponse createTokenResponse = jwtService.refreshToken(refreshSessionRequest.getRefreshToken());

        switch (createTokenResponse.getStatusCode()) {
            case SUCCESS:
                return loginServiceMapper.convert(createTokenResponse.getTokenData(), RefreshSessionResult.StatusCode.SUCCESS);
            case INVALID_DATA:
                return RefreshSessionResult.create(RefreshSessionResult.StatusCode.INVALID_DATA);
            default:
                throw new NotHandledResponseException();
        }
    }
}
