package bg.com.bo.bff.services.implementations.v1;

import bg.com.bo.bff.application.dtos.request.ChangePasswordRequest;
import bg.com.bo.bff.application.dtos.response.GenericResponse;
import bg.com.bo.bff.application.exceptions.HandledException;
import bg.com.bo.bff.commons.constants.Constants;
import bg.com.bo.bff.commons.converters.ChangePasswordErrorResponseConverter;
import bg.com.bo.bff.commons.validators.generics.*;
import bg.com.bo.bff.providers.interfaces.ILoginMiddlewareProvider;
import bg.com.bo.bff.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService implements IUserService {

    private ILoginMiddlewareProvider loginMiddlewareProvider;

    @Autowired
    public UserService(ILoginMiddlewareProvider provider) {
        this.loginMiddlewareProvider = provider;
    }

    @Override
    public GenericResponse changePassword(String personId, String ip, String deviceId, String deviceUniqueId, String rolePersonId, ChangePasswordRequest changePasswordRequest) throws IOException {
        IValidator<String> validator = new NotEqualsValidator<>(changePasswordRequest.getOldPassword(), new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.SAME_PASSWORD));
        validator.setNext(new MinLengthValidator(Constants.PASSWORD_MIN_LENGTH, new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)))
                .setNext(new MaxLengthValidator(Constants.PASSWORD_MAX_LENGTH, new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)))
                .setNext(new ContainsDigitValidator(new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)))
                .setNext(new ContainsLetterValidator(new HandledException(ChangePasswordErrorResponseConverter.ChangePasswordErrorResponse.NOT_VALID_PASSWORD)));
        validator.validate(changePasswordRequest.getNewPassword());

        return loginMiddlewareProvider.changePassword(personId, ip, deviceId, deviceUniqueId, rolePersonId, changePasswordRequest);
    }
}
