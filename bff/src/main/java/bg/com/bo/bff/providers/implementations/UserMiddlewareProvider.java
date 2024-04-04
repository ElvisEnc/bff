package bg.com.bo.bff.providers.implementations;

import bg.com.bo.bff.application.config.MiddlewareConfig;
import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.enums.response.user.UserControllerErrorResponse;
import bg.com.bo.bff.commons.enums.response.user.UserControllerResponse;
import bg.com.bo.bff.models.interfaces.IHttpClientFactory;
import bg.com.bo.bff.providers.interfaces.ITokenMiddlewareProvider;
import bg.com.bo.bff.providers.interfaces.IUserProvider;
import org.springframework.stereotype.Service;

@Service
public class UserMiddlewareProvider implements IUserProvider {

    private ITokenMiddlewareProvider tokenMiddlewareProvider;
    private MiddlewareConfig middlewareConfig;
    private IHttpClientFactory httpClientFactory;

    public UserMiddlewareProvider(ITokenMiddlewareProvider tokenMiddlewareProvider, MiddlewareConfig middlewareConfig, IHttpClientFactory httpClientFactory) {
        this.tokenMiddlewareProvider = tokenMiddlewareProvider;
        this.middlewareConfig = middlewareConfig;
        this.httpClientFactory = httpClientFactory;
    }

    @Override
    public GenericResponse changePassword(String personId, ChangePasswordRequest changePasswordRequest) {
        if (personId.equals("1"))
            return GenericResponse.instance(UserControllerResponse.SUCCESS);

        throw new HandledException(UserControllerErrorResponse.INVALID_DATA);
    }
}
